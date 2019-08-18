**Details**

The application must use the console for input and output.   
Users submit commands to the application. There are four commands. 'posting', 'reading', etc. are not part of the commands; commands always start with the user's name.  
posting: <user name> -> <message>  
reading: <user name>  
following: <user name> follows <another user>  
wall: <user name> wall  

How to run the application:   
In your command line enter : **gradle jar**  
Following with: **java -jar ./build/libs/SocialNetworkKata.jar**


**Exercise**

Implement a console-based social networking application (similar to Twitter) satisfying the scenarios below.

**Scenarios**

Posting: Alice can publish messages to a personal timeline

> Alice -> I love the weather today  
> Bob -> Damn! We lost!  
> Bob -> Good game though.  
Reading: Bob can view Alice's timeline  

Reading: User can read published messages

> Alice  
I love the weather today (5 minutes ago)  
> Bob  
Good game though. (1 minute ago)  
Damn! We lost! (2 minutes ago)  
Following: Charlie can subscribe to Alice's and Bob's timelines, and view an aggregated list of all subscriptions  

Wall: Users can view personal wall's

> Charlie -> I'm in New York today! Anyone want to have a coffee?  
> Charlie follows Alice  
> Charlie wall  
Charlie - I'm in New York today! Anyone want to have a coffee? (2 seconds ago)  
Alice - I love the weather today (5 minutes ago)      

Follow: Users can follow other users

> Charlie follows Bob  
> Charlie wall  
Charlie - I'm in New York today! Anyone wants to have a coffee? (15 seconds ago)  
Bob - Good game though. (1 minute ago)  
Bob - Damn! We lost! (2 minutes ago)  
Alice - I love the weather today (5 minutes ago)  

