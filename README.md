## GodComplex-4262Ridgebots

Hello and welcome,

This is GodComplex, an FTC library (well, more like a group of programs) that is meant to offer a more intuitive, simpler way to enable robot movement across spline curves. I built it for 4262 Ridgebots; see Credits at the bottom for the libraries and tools it relies on. This file is built to be a simple yet effective tutorial regarding this program. It's long enough to give details, and short enough that you won't bore yourself.

4262 Ridgebots and 9266 Pyrobots are teams of Pacific Ridge School, in Carlsbad, CA. Show up to some of our matches if you'd like.

Good luck, and have fun.

# Overview and Demonstration
Put simply, you put in 4 points, of x and y, and specify a heading, theta. The first and last points are your start and end. The second and third points build a Bezier Curve (shown in Curves, if you want to figure out the algorithm yourself). The robot might not explicitly pass through these secondary points, but they influence the robot's path, allowing it to maneuver around obstacles in one smooth motion.

Curves also builds an arc length lookup table when the path is created. Equal steps along a Bezier curve's parameter don't cover equal distances, so the table converts "how far along the path should the robot be" into the right point on the curve. Without it, the robot would rush through some stretches and crawl through others.


https://github.com/user-attachments/assets/20df0579-625d-4c0d-b35d-7709c5e2847d


# Hardware
The robot this was first tested on is a parallel plate chassis, with Yellowjacket motors from goBILDA. It utilizes the goBILDA Pinpoint Odometry Computer for its IMU, as opposed to the Control Hub IMU. The odometry wheels are the goBILDA 4-bar variant, although it is not hard to change it to the swing-arm variation. Personally, I recommend the 4-bar variant.

# Downloads and Setup
This was done in Android Studio, which I recommend setting up before using this system. Eventually I'll add a way to import it through gradle.

You will need every single Java file for this to work, plus these dependencies:

- FTCLib (the PID controllers come from it)
- FTC Dashboard
- A recent FTC SDK that includes the goBILDA Pinpoint and Limelight drivers

Furthermore, you will need to check the offsets of your odometry pods for the rotation system to work. Grab a ruler, and measure in millimeters. I repeat, MM, from the odo-pods to your robot center.

Next, ensure your goBILDA odometry computer is set up. You will need a test code to check which directions the odometry pods are in. After setting that up, move the robot in the direction of the arrows. The values should increase positively. If it does not, reverse the direction in CustomDrive. Additionally, make sure your motors are set up so that when going forward, all 4 motors have the same power values (which should be 1). If they are not, reverse some of them.

# Necessary Disclaimer
This library assumes you're comfortable with FTC basics like configuring hardware and reversing motors. If you're not there yet, start with the FTC documentation first.

About the imports across all the programs: most of them aren't used. They're kept because the same set covers just about any Auton or TeleOp you'd write, so feel free to copy-paste them. Note that they still have to resolve, so every file needs FTCLib, FTC Dashboard, and the Limelight classes installed to compile. If you don't use one of those, delete its imports.

# MoveScum
You only need to know two values here. MaxV and MaxA. They stand for velocity and acceleration respectively. Basically, they control how fast your robot starts to move and its top speed. If they are too high, the robot may just catapult itself into a wall, so be careful with this. Do not change anything inside the actual program, only through GodObject.

# GodObject
After setup, this should be the only program you change on a regular basis. As stated in Overview, you will see a part with 4 points. Those are where you actually control the robot movements. There are two as of right now. To add more, simply make more Curves Objects, and more MoveScums if you need. Then, call followPath() inside the Opmode if statement.

Next, you will have to tune the PID values. It's different for each robot, so the values I give will automatically be wrong for your robot. Of course, you can do it through this program, but it's recommended to do it through a separate tuner TeleOp and the FTC dashboard. Tutorials can be found online.
kV is its feedforward component, basically just gives the robot an extra kick when it moves. Do with it whatever you wish.

Now, apologies for whiplash, but your movement values are now in INCHES. I repeat, INCHES. A foam mat is 24 inches x 24 inches. I expect you to figure out the rest. They are in doubles, so feel free to mess around with decimals.

# followPath()
followPath() takes 3 values:

path: the path you want it to move along, should be a Curves object

profile: the "motion profile" of your path following. Should be a MoveScum object

heading/theta: What angle you want your robot to be facing. The robot turns toward this heading while it drives the path, not before it. 0 will be the direction you face it when you init the robot.

# Credits
- **FTCLib:** the PIDController class used for x, y, and heading correction.
- **goBILDA Pinpoint Odometry Computer and its driver:** position and heading tracking.
- **AI tools (Claude and Gemini):** drafted the original followPath() loop in TheGodObject, including the numbered step comments still in that method. I integrated it with Curves, MoveScum, and CustomDrive. Some comments from the draft were removed on 10/11/2026. AI tools also helped me verify concepts and check for bugs, including swapping Math.pow for direct multiplication, the heading setpoint fix, and resetting the PID controllers between paths (both 10/11/2026).
- Everything else, including the Bezier math and arc length table in Curves, the motion profile in MoveScum, and the drive code in CustomDrive, is my own work.

# Thanks
That should be all the important bits.
Special thanks to 9266 Pyrobots and 4262 Ridgebots for moral support. I expect you all to reach Worlds next year or I'll be disappointed.
This was a homebrew project for my team. I expect my teams, if no one else, to be using this library at least until a better option is found or created.

Zhixun Wang, out.
