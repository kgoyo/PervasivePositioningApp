# Mandatory Project 2 – Efficient Position Updating

This project should help you learn to implement and evaluate systems based on techniques for energy efficient pervasive positioning.

You should describe your programs and experiments in a short report (1500 words plus figures). The report should include a short description of your design and implementation choices, and a description of how to use the programs. The TAs must be able to run your client programs on an Android Phone and server programs on a CS Linux machine (unless you have made special arrangements with the TAs). You should also describe and illustrate the experiments you have performed.

The report must be handed in October 6, 8.00. Upload the report as a single pdf-file and your code + kml files as a zip file. Files must be named “Report.pdf” and Code.zip”.

The project will not be graded but its completion is essential for passing the course and you should be able to present your work and the underlying theory and algorithms at the oral exam.

The project should be done in your groups.

# Part I

You should implement the following client programs on an Android Phone. You can use any programming language you like, but the programs must run on an Android Phone. I would propose to use either Java or Python for ASE.

## Mandatory

Periodic Strategy: Make a client program that collects GPS-fixes every XX seconds. You should be able to start the program with an integer parameter that controls the XX variable. The collection of GPS-fixes should be stored in a suitable named file on the phone.
Distance-based strategy: Make a new program or extend the former with a new feature that collects a GPS-fix whenever you see a new fix that is YY meters from the previously collected fix. You should be able to start the program with an integer parameter that controls the YY variable. The collection of GPS-fixes should be stored in a suitable named file on the phone. You have to filter the GPS positions in your own code – do not use features in the OS or elsewhere to do the filtering for you. Hint: The program should observe the distance between the GPS fixes it receives from the built-in GPS and log a position update to the file only if the distance threshold to last stored position has been passed.
Maximum-speed Strategy: Make a new program or extend the former with a new feature that requests fewer GPS fixes. You should use a threshold YY and a configurable maximum speed ZZ of the device. Use the maximum speed to calculate when a new GPS-fix is needed. You should store all GPS-fixes requested from the built-in-GPS.
Movement-aware Strategy: Make a new program or extend the former with a new feature that requests fewer GPS fixes. You should still use a distance threshold YY and a maximum speed ZZ. Furthermore, you should use accelerometer readings to indicate whether the device is moving at all – thereby adding time until next GPS request is needed. You should store all GPS-fixes requested from the built-in-GPS.
Make a script/program that can take your log files and create a kml file where each GPS fix is represented as time-stamped placemarks and logs the number of fixes reported.


### Optional                   

Make a server program that can accept a TCP connection or HTTP post and log the incoming GPS fixes to a KML file using your script/program from above.
Extend the client program to set up a TCP connection or do a HTTP post to the server program on a configured IP address and port. When the program receives a GPS fix from the built-in GPS it should send a position update over the connection to the server instead of logging to a file.

# Part II

In the second part of this project you should design and implement experiments to examine the number of GPS fixes and uplink position updates needed to track a target. You might want to extend your client program(s) with a small GUI where you can choose between the different positioning strategies from above and set configurable parameters.

## Mandatory

Make a Ground Truth program for your phone that collects timestamps for every waypoint reached (push of a button). The program should gather enough data to enable you to interpolate (using the timestamps) a pedestrian’s position at any time during an experiment. Hint: During experiments - make waypoints at the beginning of a stair and the end if you change floors. Also make an effort the keep the same pace between waypoints.
Collect generated logs, and maintain KML files at the server (if the optional parts of Part 1 are implemented) and logs on the phone for the following scenarios assuming that you are tracking a pedestrian target. For each scenario you should collect at least 10 minutes of data and the associated Ground Truth data. Make sure that you walk more than 200 meters away from the starting point. You should include a few minutes stopping time in the walk.
Run the client with the periodic strategy with a time interval of 1 second.
Run the client with the distance-based strategy with distance configured as 50 meters.
Run the client with the maximum-speed strategy with distance configured as 50 meters and maximum speed as 2 m/s.
Run the client with the movement-aware strategy with distance configured as 50 meters and maximum speed as 2 m/s.
Create screenshots using Google Earth for each of the scenarios of the collected KML files. The screenshots should include a path that marks the actually walked route (Ground Truth). Comment on the results in the report and discuss how GPS errors impacted the results.
Make a list with the following entries for each scenario: strategy, number of GPS fixes requested, number of GPS fixes stored, time span, GPS fixes requested per second, GPS fixes stored per second and comment on them in the report with respect to relevant literature. Discuss what pervasive positioning applications the different strategies are relevant for.
Plot a graph with the Cumulative Distribution functions of all 4 strategies – and comment on any findings and oddities in your results. Calculation method: To calculate position error Interpolate Ground Truth for each second. Then calculate position error distance to every stored GPS fix.


### Optional

Plot a graph with the Cumulative Distribution functions of all 4 strategies – and comment on any findings and oddities in your results. Important! Compare error distance calculation methods.
Calculation method: To calculate position error find the timestamp T for each position fix and interpolate between timestamps in the corresponding Ground Truth file to find the Ground Truth estimate to calculate position error distance from. Important! Compare error distance calculation methods.
