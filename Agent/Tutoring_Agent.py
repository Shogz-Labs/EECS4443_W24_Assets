import streamlit as st 
import os 
# HuggingFace Endpoint
os.environ['HUGGINGFACEHUB_API_TOKEN'] = '#### Place your HuggingFace API Key Here! ####'
from langchain_community.llms import HuggingFaceEndpoint
from langchain.memory import ConversationBufferWindowMemory
memory = ConversationBufferWindowMemory(k = 100)
# Agent 
from langchain.agents import AgentExecutor, Tool, LLMSingleActionAgent, AgentOutputParser
from langchain.schema import AgentAction, AgentFinish
from langchain.chains import LLMChain
# Tools 
from langchain_community.tools import DuckDuckGoSearchRun
# Prompts 
from langchain.prompts import StringPromptTemplate
from typing import List, Union
import re

# Resources:
# https://python.langchain.com/v0.1/docs/modules/agents/quick_start/
# https://www.youtube.com/watch?v=6UFtRwWnHws
# https://huggingface.co/docs/transformers/en/main_classes/text_generation
# https://huggingface.co/HuggingFaceH4/zephyr-7b-beta

# Module 1: Define the LLM Backbone of the Agent
llm = HuggingFaceEndpoint(
    repo_id = "HuggingFaceH4/zephyr-7b-beta",
    temperature = 1,
    max_new_tokens = 10000
)

# Module 2: Define Tools
search = DuckDuckGoSearchRun()

def search_android_docs(input_text: str):
    
    return search.run(
        f"site:developer.android.com/develop {input_text}"
    )

def search_stackoverflow(input_text: str):
    return search.run(
        f"site:https://stackoverflow.com/ {input_text}"
    )

search_android = Tool(
    name = 'android-search',
    func = search_android_docs,
    description = 'Search for information about Android Studio. For any questions about Android Studio, you must use this tool.'
)

search_so = Tool(
    name = 'stackoverflow-search',
    func = search_stackoverflow,
    description = 'Search for information related to compile-time errors or debugging. This is a more general-purpose tool.'
)

tools = [search_android, search_so]
tool_names = [tool.name for tool in tools]

# Module 3: Define Prompt 
template = """You are an expert Android Studio programming tutor who provides descriptive step-by-step instructions written in Markdown format. Do not write Kotlin code:

{tools}

Use the following format:

Question: The input question you must answer 
Thought: You should always think about what to do 
Action: The action to take, should be one of [{tool_names}]
Action Input: The input to the action 
Observation: The result of the action 
... (this Thought/Action/Action Input/Observation can repeat N times)
Thought: I now know the final answer to the original input question
Final Answer: The final answer to the original input question in a comprehensive step-by-step format. Only reference suggested related to the Android Studio IDE. Do not reference your agent tools.
Write your answers in markdown format.

Begin! Remember to answer as a compassionate tutor when giving your final answer. Provide encouragement and step-by-step solutions with code where applicable!
Question: {input}
{agent_scratchpad}
"""


# We use the following code to help guide the LLMs output in order to adhere to the above prompt.
# Do note that this will be unnecessary if you use a GPT model.


class CustomPromptTemplate(StringPromptTemplate):
    template: str
    tools: List[Tool]

    def format(self, **kwargs) -> str:
        intermediate_steps = kwargs.pop('intermediate_steps')
        thoughts = ''
        for action, observation in intermediate_steps:
            thoughts += action.log
            thoughts += f"\nObservation: {observation}\nThought: "
        kwargs['agent_scratchpad'] = thoughts 
        kwargs['tools'] = '\n'.join([f"{tool.name}: {tool.description}" for tool in self.tools])
        kwargs['tool_names'] = ', '.join([tool.name for tool in self.tools])
        return self.template.format(**kwargs)

class CustomOutputParser(AgentOutputParser):
    def parse(self, llm_output:str) -> Union[AgentAction, AgentFinish]:
        if 'Final Answer' in llm_output:
            return AgentFinish(
                return_values = {'output': llm_output.split('Final Answer:')[-1].strip()},
                log = llm_output
            )
        # Parse the action / action input
        regex = r"Action\s*\d*\s*:(.*?)\nAction\s*\d*\s*Input\s*\d*\s*:[\s]*(.*)"
        match = re.search(regex, llm_output, re.DOTALL)

        if not match:
            raise ValueError(f"Could not parse LLM output: {llm_output}")
        action = match.group(1).strip()
        action_input = match.group(2)
        return AgentAction(
            tool = action,
            tool_input = action_input.strip(" ").strip('"'),
            log = llm_output
        )
    

prompt = CustomPromptTemplate(
    template = template,
    tools = tools,
    input_variables = ['input', 'intermediate_steps']
)

output_parser = CustomOutputParser()

# Module 4: Define Chain

llm_chain = LLMChain(
    llm = llm,
    prompt = prompt
)

# Module 5: Define Agent
agent = LLMSingleActionAgent(
    llm_chain = llm_chain,
    output_parser = output_parser,
    # Stop at Observation and put it into the custom parser
    # This is when the tool is used
    stop = ["\nObservatation:"],
    allowed_tools = tool_names
)

agent_executor = AgentExecutor.from_agent_and_tools(
    agent = agent,
    tools = tools,
    verbose = True,
    memory = memory
)

# Module 6: UI Configuration

st.title('LE/EECS 4443 Tutoring Agent')
if "messages" not in st.session_state:
    st.session_state.messages = [
        {
            'role': 'ai',
            'content': 'Hello, I am an Android Studio LLM! While I may not be perfect, hopefully, I can help provide some assistance with debugging outside of the lab hours! Please ask me a question :3'
        }
    ]
for message in st.session_state.messages:
    with st.chat_message(message['role']):
        st.markdown(message['content'])

if message := st.chat_input('Ask your questions here!'):
    with st.chat_message('user'):
        st.markdown(message)
    st.session_state.messages.append(
        {
            'role': 'user',
            'content': message
        }
    )
    response = agent_executor.invoke(
        {
            'input': message
        }
    )['output']

    with st.chat_message('ai'):
        st.markdown(response)
    st.session_state.messages.append(
        {
            'role': 'ai',
            'content': response
        }
    )
