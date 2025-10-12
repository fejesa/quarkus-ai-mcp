# How I Built an AI-Powered Template Generator with Quarkus, LangChain4j, and Ollama

## Introduction
Businesses have customers, right? And many of them provide online services — shopping, banking, telecom, insurance, and so on.  
But communicating with customers is never easy, especially in the digital era. Which channels are the right ones for a given business? Instant messaging, voice calls, or classic text-based messages?  
What should the tone and style be — formal, friendly, or conversational?  
And most importantly: **what exactly does the business want to communicate?**

We can agree that most online services rely heavily on **text-based messaging** when communicating with their customers.  
Sometimes the messages are sent directly via email, other times through a **dedicated in-app messaging system**, where customers have a personal inbox.  
Typically, these messages are **HTML formatted** — for good reason: better readability, structured layout, support for images and hyperlinks, and a more professional look.

Every business has its own communication style — formal or informal, personal or official — and all customer messages need to follow that same consistent structure and tone. This helps customers easily recognize and understand communications coming from the business.

Now, imagine a company that has **dozens or even hundreds of customer notification types.**  
Take a **Financial Services** example — customers might receive messages like:
- Account opening confirmation
- Loan application received
- Loan approval notification
- Overdue payment notice
- Account statement available
- Security alert (e.g., suspicious activity detected)

Each message type is typically based on a **template**, and each template contains **placeholders** (like `[[customer_id]]`, `[[branch_name]]`, `[[contact_phone]]`, etc.).  
Whenever an event occurs that triggers a notification, the messaging system fills those placeholders with real customer data and sends the message.

So far, so good. But this raises a few questions:

- How can a business ensure that all message types share the same format and style, especially when there are hundreds of templates?
- How many placeholders should exist — and which ones should be used in each template?

Uhh… it’s cumbersome and error-prone. It requires **a lot of manual work** and coordination between teams.

And this is exactly where **AI** can step in — not necessarily to create the customer messages themselves, but to **standardize and accelerate the process of message template generation!**

---
## Use Case
Let’s imagine a **messaging platform for a bank** that communicates with its customers.  
The standard format is **HTML**, and all message types must follow the same tone, structure, and styling.

Templates contain reusable placeholders such as customer name, ID, branch name, contact details, and so on.  
When a new business case arises — for example, *“A customer wants to request an additional Mastercard”* — an admin just needs to describe the use case in natural language, like:

> “I need a template that describes the process by which a customer can request an additional Mastercard from the bank.”

Optionally, the admin can provide a draft version of the template or simply leave it empty — and the AI takes care of the rest.

The AI analyzes existing templates, retrieves placeholder definitions, and generates a **new, well-structured, stylistically consistent HTML message** that fits perfectly with the other templates.

---
## Technology Choices
For this proof of concept, I chose a stack that combines **modern web development** with **AI integration** — and makes experimentation genuinely enjoyable:

- **Angular with PrimeNG** – A perfect fit for building rich, interactive, and scalable single-page applications (SPAs). [PrimeNG](https://primeng.org/) provides a great set of ready-to-use UI components.
- **[LangChain4j](https://docs.langchain4j.dev/)** – An open, composable Java framework that defines a standard interface for LLMs, tools, and data sources. It makes building AI-driven workflows in Java straightforward.
- **[Ollama](https://ollama.com/)** – A local LLM runtime that’s simple to use and well-integrated. For this experiment, I used the `gpt-oss:20b` model, which offered a great balance between performance and quality.
- **[Quarkus](https://quarkus.io/)** – A cloud-native Java framework that integrates seamlessly with LangChain4j. Registering an AI service is annotation-based, and exposing tools is as easy as annotating your code. And honestly — developing with Quarkus is just *fun*.
- **[Quinoa](https://github.com/quarkiverse/quarkus-quinoa)** – A Quarkus extension that simplifies building and serving Angular (or other SPA) applications directly from your backend. It eliminates much of the setup hassle.

---
## Architecture
Angular applications typically run on a Node.js server.  
However, with **Quarkus’s Quinoa extension**, Quarkus automatically starts the Node.js server when the application runs, seamlessly serving the Angular app.

Since Angular operates on the client side, backend communication happens via **REST APIs**.  
Quarkus automatically generates an [OpenAPI](https://www.openapis.org/) schema, which I used with [Orval](https://orval.dev/) to generate RESTful clients as Angular services (though other tools could be used as well).

For the editor, I chose [Quill](https://quilljs.com/), a free, open-source, WYSIWYG rich text editor with a modular, customizable architecture. PrimeNG conveniently includes an integrated Quill-based editor, which made the frontend implementation straightforward.

Message templates are stored as files (for simplicity), while **template metadata and placeholders** (name, description, etc.) are stored in a **PostgreSQL database**.  
These are made accessible to the AI model via the [Model Context Protocol](https://modelcontextprotocol.io/) (MCP) — a standard that allows language models to call external tools (such as database queries, APIs, or computations) during reasoning.

![Architecture Diagram](./docs/architecture.png)

Here’s how the flow works:

1. The user requests template generation from the frontend.
2. A REST call is made to the registered AI service in Quarkus.
3. The AI service invokes the configured MCP tools to fetch context — existing templates and placeholder definitions.
4. The model analyzes this information and generates or updates the HTML template accordingly.
5. The generated template is returned and rendered in the frontend’s editor.
6. If needed, the user can manually fix invalid tags or fine-tune the content.

The final output always follows the **same structure, style, and placeholder rules** as other templates — ensuring consistency across the entire messaging platform.
👇
