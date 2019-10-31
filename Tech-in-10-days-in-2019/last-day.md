### Crash course
* [x] Terraform
    - Manage config and state in code.
    - Dry run before execute.
    - Check drift.
    - Mix with Ansible/Kubernetes.

* [x] Vault
    - Manage secrets.
    - Authenticate with Github.
    - Manage policies in Terraform.

* [x] Retrofit client, OkHttp, resilience4J, 
    - [x] Retrofit -  
        - Converts API to Java method calls using annotations. Provides **type-safety**.
        - Supports sync/async, converters, etc
        - Uses `okHttp` for HTTP calls if available. Can be configured to use other HTTP clients.
        - [https://www.vogella.com/tutorials/Retrofit/article.html]
    - [x] OkHttp
        - An efficient by default HTTP client.
        - > OkHttp has HTTP/2, a built-in response cache, web sockets, and a simpler API. It’s got better defaults and is easier to use efficiently. It’s got a better URL model, a better cookie model, a better headers model and a better call model. OkHttp makes canceling calls easy. OkHttp has carefully managed TLS defaults that are secure and widely compatible. Okhttp works with Retrofit, which is a brilliant API for REST. It also works with Okio, which is a great library for data streams. OkHttp is a small library with one small dependency (Okio) and is less code to learn. OkHttp is more widely deployed, with a billion Android 4.4+ devices using it internally.
        - [https://github.com/square/okhttp/]
    - [x] Resilience4J.
        - Replacement for deprecated Hysterix.
        - Use it for Circuit-breaking, Retrying, Rate-limiting, Bulkhead, caching, time-limit
        - Supports bunch of things like Retrofit, Kotlin, Reactor, Spring, etc.
        - https://resilience4j.readme.io/docs/examples-5
        - https://www.baeldung.com/resilience4j

* [] R2DBC
* [] Project loom (JVM) vs Kotlin Coroutines
