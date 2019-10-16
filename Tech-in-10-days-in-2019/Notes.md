#### Service mesh
- Abstraction of common functionality like service discovery, encryption, load-balancing, fault-tolerance (resilience & retry), telemetry.
    - Netflix approach - via libraries - eg: Hysterix
    - sidecar approach - via sidecars  - eg: envoy // Better as it is agnostic of the service technology and language. Better control.
- 2 planes
    - data plane - the core functionality of the  network and it's behavior. This is where data flows.
    - control plane - configuration to control data plane behavior. Administration.
- https://thenewstack.io/history-service-mesh/
- https://www.nginx.com/blog/what-is-a-service-mesh/