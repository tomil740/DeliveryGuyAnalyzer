<h1>Delivery guy analyzer (MVP version) : </h1>
<p>Delivery guy analyzer app help an delivery guy to track its working data and provide him with some essential data about his working efficiency and the income in relation to his work / in general , the data can be adjust to any wanted time specific time frame or to an general all time statistics . The app build to be very battery efficient together with simple and clear preps dynamic UI its very convent for the user to track its data and know exactly how much its work worth in actual money at any wanted time frame </p>
<img src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/705c8ebd-1849-45a7-88f1-1121c52aa85b" width="170 height = "35">
<img src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/91d064ce-4a52-4990-831e-d3a7398e0ebf" width="170 height = "35">

<h1>Project demo :</h1>
<img src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/5187a668-f3f0-4f41-88b5-e695aecf0aea" width="170">
<img src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/eb7ac06a-7b04-4d4e-828c-075cfcb947ab" width="170" >
<img src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/fd0958a1-d1bf-4188-aec7-6e1f494a0fe5" width="170" >
<img src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/b1a30d7c-a183-4e3c-9c3e-41f2c2547bb7" width="170" >
<img src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/093ccf8f-fc79-48c6-8d8b-63deb916479f" width="170" >
<img src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/3510150b-5747-4ef9-8300-1dae5ce19850" width="170" >
<img src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/9871b657-3978-46dc-9e48-7bafc6ad8f03" width="170" >
<img src="https://github.com/tomil740/DeliveryGuyAnalyzer/assets/126959122/ff13ede2-bb01-4b19-807a-4351e277222f" width="170" >

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









