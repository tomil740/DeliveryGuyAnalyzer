<h1>Delivery guy analyzer (MVP version) : </h1>
<p>Delivery guy analyzer app help an delivery guy to track its working data and provide him with some essential data about his working efficiency and the income in relation to his work / in general , the data can be adjust to any wanted time specific time frame or to an general all time statistics . The app build to be very battery efficient together with simple and clear preps dynamic UI its very convent for the user to track its data and know exactly how much its work worth in actual money at any wanted time frame </p>
<img src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/705c8ebd-1849-45a7-88f1-1121c52aa85b" width="170 height = "35">
<img src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/91d064ce-4a52-4990-831e-d3a7398e0ebf" width="170 height = "35">

<h1>Project demo :</h1>
<img width="600" alt="Screenshot 2024-06-12 at 14 26 06" src = "https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/4381fabd-3b00-495a-8918-d5ebdec93042">
<h3>Updating data through the live builder or the typed builder :</h3>
<img alt="Screenshot 2024-06-12 at 15 42 20" src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/315b2962-899c-40a1-afe0-4a1376166a31" width=30% height=85%>
<img alt="Screenshot 2024-06-12 at 15 48 59" src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/ef597247-b03c-47a6-bef2-d48160739574" width=55% height=85%>

<h1>How to use :</h1>
<img width=100% alt="Screenshot 2024-06-12 at 14 26 06" src = "https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/89f6f3fd-a203-4dcb-b05a-4d2f6c099042">
<h1>Define and structure the app idea :</h1>
<p>
  After defining the idea to the core of it ,will start the tangible phase by defining the main matched use case to the core idea.
Which is to update the data and to get the target data.
Around those we start implemnitating some basic sketch of the solution (the app) until this phase target which is a basic mockup of the app that implement our defined use cases.
</p>
<img src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/a2f64bd1-0cf3-45bb-a1e2-3dcb1d8ad601" width="170">
<img src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/7f11168c-2197-4c1d-ab36-b53f5e29cbce" width="170">
<img src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/0f083b66-baca-4374-9600-22ca867339bd" width="170">
<img src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/281e9740-41f1-49b0-8b80-3492c764f668" width="170">
<h3>Implement funcunalty :</h3>
<p>
  That will start by defining our data structure , pick the ideal tecnologics and build patterns according to the all around considerations to get the ideal behaviours all around .
At this phase we will start connecting the dots we place before and accordingly defined the ideal platforms and build patterns to the actual build itSelf which constitutes as real app skeleton. 
</p>







<h1>teachnolgics and build review :</h1>
<p>
  The project is build to work perfectly in a native level of performance and UX in all ranges of devices from  both IOS and Android by implemnitating matched dynamic them components to each platform with match UI components according to material 3 and native look matched material components for native IOS feel   the project is build mostly on pure kotlin code and libraries in order of make most of it shred between both platforms
</p>
<img src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/62863d48-5369-480c-a72b-85ec856a2ce1" width="125" height = "42">
<img src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/10469cb7-215f-4054-b985-c22bb45e672b" width="125" height = "42">
<img src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/1ceec314-0f71-4da4-81ed-920fa40afb40" width="125" height = "42">
<img src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/a781c387-7446-42d6-bab8-6d22a82e7b7d" width="125" height = "42">
<img src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/82456da1-3ebe-4bd9-a40d-eeb6e5760649" width="125" height = "42">
<img src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/bcdf4beb-ab11-41e8-936c-ffb3df5f6a01" width="125" height = "42">

<h3>Build architecture and patterns pick :</h3>
<p>
  The project build according to the MVVM build architecture with clean architecture at the main directory of each module we will order the code by features and inside every feature implement the regular layer separation of data presentation and domain …  , for communicating between high to low level we will be using repository pattern with dependency inversion (all tough at this version there is no need for an repository this pattern was implement to make it convent for new features in the coming future).
In order of keep the project  as clean and simple to understand and expended with new feature we implement this kind of structure mentioned above.
</p>
<h3>Data and performance point of view :</h3>
<p>
  As mentioned the app build to be easy to expand,easy to understand and well preform as possible   In order of achieving that our data/domain level are build as mentioned before to be expended in the feature , that is expressed with <br>
* the repository pattern that allows easy replacing of any data sources <br>
* The local db itSelf which is build at at the data layer fully for easy swap between our current noSQL implementation(mongo db realm at that case)  into any SQL regular db… <br>
in general the project is build with high priority to be as modularize as possible for coming future updates 
</p>

<h4>Time complexity :</h4>
<p>
  The main use case of our project is to summarize data from our local db ,which means the mean demanding process are pulling and summaries the data.
* all of the matched function were build carefully at the matched threads in order of making sure the UI will always be fast and responsive even in the most edge cases or path unexpected bugs 
* The Time complexity of the most demanding process will be N linear which is the beset we can achieve here, this level of time complexity let us even at the most edge case to achieve an fast responsible app performance 
</p>

<h4>Main use case data models implementation :</h4>
<p>
The data process will have two starting points <br>
* Pulling data <br>
* Pushing data <br>
</p>
<h5>Pushing data: </h5>
<p>
*We will start with [1] , the user input data to the app -> 
*then map [1] straight to [4] , while the user types the data ,on every user data change the app will recalculate [4] object accordingly and     present the matched data to the user  ->  * on user submit the app will take the matched [4] (if its matched the legal attributes value to insert declare) and convert it to [2] which is     data object that will update the db data … 
</p>
<img src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/f6799707-fd4a-411b-b168-2fe0e39a43db" width="650" height = "160">
<h5>Pulling/Presenting  data :</h5>
<p>
We will pull data in two methods <br>
* pull single work session (one object) <br>
* pull a list of related object according to the demand <br>
After pulling data we will want to present it at most cases (or applying some calculations on it)<br>

Presenting data :<br>
Basically to present data we will aim to get any of the mentioned objects into the 4 object frame according to the demand 
single object : Presenting single object is pretty much as we mentioned on the builder above ,we will take the data obj we pulled [2] or the builder input [1] and map it straight to [4] <br>
* The use case of that as for this version is on the builder (as mentioned above) as the finally step of the collection method…<br>

Collection of objects :<br>
Presenting an collection of object will be actually two steps , 1) sum up the data to an single object with the matched sub objects  attribute and 2) will be to present it as a single object …
Sum up the collection : the data will be pull with a query according to the user demand for a X month as example then the summaries use case will take this data and calculate from it single workSum object [3] represent all of the list data -> from there all that has left to do is to map it into [4] …
</p>
<img src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/e4171f85-2909-4fb7-854f-a40ff6f59b32" width="800" height = "350">











