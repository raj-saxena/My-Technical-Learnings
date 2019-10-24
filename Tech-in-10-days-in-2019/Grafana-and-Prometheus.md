# Grafana and Prometheus

## Grafana
* Grafana allows you to query, visualize, annotate, alert on and understand your metrics (and logs).
* Supports a lot of ways(graphs) in which you can represent data.
* No time series storage support. Grafana is **only a visualization solution.**
* Can have a variety of data stores like Graphite, Elasticsearch, Google Stackdriver, PostGres, Prometheus, etc.

## Prometheus
* Prometheus is a systems and service monitoring system. 
* It collects metrics from configured targets at given intervals, evaluates rule expressions, displays the results, and can trigger alerts if some condition is observed to be true.
* It has a multi-dimensional data model (timeseries defined by metric name and set of key/value dimensions).
* When does it fit?
> Prometheus works well for recording any purely numeric time series. It fits both machine-centric monitoring as well as monitoring of highly dynamic service-oriented architectures.
* When does it not fit?
> Prometheus values reliability. You can always view what statistics are available about your system, even under failure conditions. If you need 100% accuracy, such as for per-request billing, Prometheus is not a good choice as the collected data will likely not be detailed and complete enough.


**Grafana** and **Prometheus** work really well together. There are other timeseries stores like **Graphite** but Prometheus is pretty mature and feature rich in that space. 

**APM capabilities** need to be explored - open source (https://openapm.io) vs proprietary.

Need to figure out the cost of setting up Grafana/Prometheus vs using something like Datadog.

### References
* https://github.com/grafana/grafana
* https://github.com/prometheus/prometheus
* https://prometheus.io/docs/introduction/overview/
* https://www.loomsystems.com/blog/single-post/2017/06/07/prometheus-vs-grafana-vs-graphite-a-feature-comparison