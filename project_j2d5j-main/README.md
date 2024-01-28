# My Personal Project - day logger

## Project proposal

this application allows the user to enter a daily log of their mood, activities, 
daily habits, and other fields. Then that data can be presented to them in graphs ans statistics 
so that they can see the patterns in their behaviour, etc. This application can be 
used by people for a variety of reasons. If someone has a habit that they want to 
stay consistent with, they can track that and stay motivated. If someone wants to 
see how many times a month they eat out, they can see that. It could also be used as a daily 
mood tracker. this project is of 
interest to me because I used to have a paper planner that had a daily habit tracker,
but I found it bothersome to carry around a planner, and take it out and mark down 
whether I had done something or not, so I stopped using it. Having it as accessible on my computer would make things 
easier to access. creating an application that can do those things would actually be 
useful.

## User stories:
- as a user, I want to be able to enter daily habits for any given day:
  - whether or not I went to the gym
  - how many glasses of water I drank
  - how many hours I spent on my phone
  - how many pages I read
  - mood on a scale of 1-5
- as a user I want to be able to see the statistics of any individual day
- as a user I want to be able to see a list of the days that I entered a log
- as a user I want to be able to see all of the data's statistics I entered for any given month
  - the percentage of days that I went to the gym
  - the average of how many glasses of water I drink in a day
  - the total number of pages I read
  - how average of how many hours I spend on my phone in a day
  - the overall mood for the month

- as a user, I want to be able to have the option to save my log entry to the log
- as a user, I want to be able to have the option to reload my log and continue from there

## Instructions for Grader (phase 3)
- You can generate the user story "adding multiple Xs to a Y" by...
  - filling out the text fields and clicking the button labeled "submit log!" to add the day to the log
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by...
  - clicking on any of the dates in the panel with logs for previous days to view the information for that day
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by...
  - clicking on any of the months to view the information for that month
- You can locate my visual component by...
  - the image in the centre of the application
- You can save the state of my application by...
  - clicking the button labeled "load previously saved data"
- You can reload the state of my application by...
  - clicking the button labeled "save all new changes"


## Phase 4: Task 2
Wed Nov 29 11:03:36 PST 2023
Loaded log from file
Wed Nov 29 11:03:41 PST 2023
Showed info for day: 2023-11-28
Wed Nov 29 11:03:41 PST 2023
Showed info for day: 2023-11-28
Wed Nov 29 11:03:43 PST 2023
showed info for month: 11
Wed Nov 29 11:03:43 PST 2023
showed info for month: 11
Wed Nov 29 11:04:01 PST 2023
Day added to log.
Wed Nov 29 11:04:04 PST 2023
Saved today's log to file

## phase 4: Task 3
  If I was given more time to refactor my project, there are a few different changes I'd consider making. I could 
  refactor my log class in a way that would organize the individual day logs better. I could create an enumeration 
  with all of the different months, and separate the day logs within the log class accordingly, similar to what was 
  done in one of the practice problems. This would be useful in fulfilling some of my user stories that require 
  filtering by month, the way I did it is a little longer, so this would help to simplify and organize things.
  I could also create an interface called Writable in the persistence folder, as that was what was done in the 
  json serialization demo, and then had my Day class and Log class implement it. This would help everything be more 
  uniform, and ensure that the classes that are supposed to be writable, actually are writable.