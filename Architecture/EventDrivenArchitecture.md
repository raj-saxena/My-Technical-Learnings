# Understanding Event-Driven Architecture
	* Has events that are fired when things happen.
	Say, there is an insurance system that recalculates insurance premium based on customer location. 
	There is a `customerService` and a `insuranceService`. 
	Any time a customer address is changed, insuranceService should calculate a new premium.
	* `Creator` - creator of an event.
___
## Patterns of Event based systems.
### Event Notification
	* Events vs commands - Both are messages that can potentially cause some action.
		- Commands are dictatorial. Service creating event need to be aware (coupled) with the consumers and ask them to act.
		- Events are emitted and creator is decoupled from the consumers. Creator can write the event to a queue and Subscribers may choose to act for an event.
	* Advantages
		- Loose coupling. In our eg, the customerService need not worry about calling insuranceService.
		- services can change independently. The teams responsible for one service need not ask for changes in the other service.
	* Disadvantages
		- No statement or easy flow of overall behavior and control flow as things are happening out of order in the system. Eg: a GUI app.
	
### Event Carried State Transfer
	* If the event has insufficient information, the consumer might have to talk again to the creator. For eg: if the event says *addressChanged*, the insuranceService now needs to ask customerService the old and new address of the customer. Instead, if the creator can send both old and new address with the event, there is no need for 
	* Even though this inverts the dependency, we can further reduce the dependency by sending enough information with the event. Thus, it could be `Event-carried State Transfer`.
	* Further, the consumer can keep a local copy of the event data so that it can avoid calling the creator.
	* Advantages
		- Reduced load on creator. 
		- Faster performance of consumers as it need not make a network call to creator.
		- High availability (CAP theorem).
	* Disadvantages
		- Duplicated data.
		- Low consistenncy / eventual-consistency (CAP theorem).
### Event Sourcing
	* Preserving events as logs so that they can be replayed to create the application state.
		Eg: Redux?, Git (for code), counting ledgers
	* Advantages
		- Replay of logs
		- Audit
		- Debuging
		- State transitions
	* Disadvantages
		- Handling external system responses.
		- Event schema changes with code changes.
### Command Query Responsibility Segregation (CQRS) 
	* Separate read and write to/from your event store.

___
* Reference: 
	- [Martin Fowler's talk](https://www.youtube.com/watch?v=STKCRSUsyP0)
	- [Martin's wiki](https://martinfowler.com/articles/201701-event-driven.html)

