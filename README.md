# habitTracker
My first android kotlin MVVM app. Used ROOM, flow, MPAndroid (RoundedChart), DiffUtil. 

## I have simple DB model
<img width="680" alt="dbModel" src="https://github.com/krakeljur/habitTracker/assets/125911670/ef22c7ce-2b4c-4665-aae1-d639f4a6065c">

## My functional requirements for this project:
1. When loading, the mobile application should display a list of created habits;
2. the mobile application must display the name of the habit created by the user;
3. the mobile application must provide the user with the opportunity to delete/create a new habit;
4. The mobile application must provide the user with the opportunity to add/delete a relapse (case of habit);
5. the mobile application should display to the user a list of relapses of the selected habit (date of relapse, user comment of relapse);
6. The mobile application must provide the user with the ability to edit the habit (change the name);
7. The mobile application must provide the user with the ability to edit a relapse (change the date and comment);
8. The mobile application should display the time elapsed since the last relapse (in the format of year/month/days/hours/minutes), and the system should also automatically update the time after editing the list of relapses (automatically select the last relapse to count from it);
9. The mobile application should graphically display the trend of relapses (The system should automatically update the graphical display after editing the list of relapses);
10. The mobile application must support English and Russian languages.
