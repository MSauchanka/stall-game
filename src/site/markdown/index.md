## About

**AEM Driver** is a client library for manipulating **FW AEM** instances.

It provides Groovy/Java API to manipulate with **FW AEM** state via **Granite UI** admin consoles.

### Goals

Cover most of specific **FW AEM** behaviour in one place, to simplify creation of automated tests and demos.

Provide neat API, based on patterns and conventions, which will be easy to learn

Provide a fallback to use underlying tools, like Selenium when **Driver API** isn't enough

Provide stability level around zero failures on thousand executions

To provide stable library, there is need to have run tests as much as possible,
and to provide rescue ways to workaround small issues with user interface.

Stability is more important than time. After stability time effectiveness stands.

### Scope

This library dedicated for Franchise Web AEM (hereinafter **FW AEM**),
which means that its stability is guaranteed only for AEM instances with FW application on top.
Even when it may works for pure AEM instance it must be treated as a coincidence.

### Roadmap

 - Images management
 - Pages (without components) management
 - YouTube videos management
 - In Page components management


### Precondition

User `aem-driver` with password `aem-driver` need to be created on AEM instance