Lang871 
===========

Overview
--------
This is an interpreter for the Lang871 Hebrew language. The interpreter is written in pure Java. It can parse any mathematical or logical expression and returns a result. An expression can contain variables that can be supplied before the expression is executed and the result returned. 

### Supported Commands 
* Print - ___ הדפס
* Print line - ___ הדפסש
* Variable defining - ___ = ___ הגדר
* Variable resetting - ___ = ___ הצב or just ___ = ___
* If conditions - ___ אם ___ אז
* Functions - 
	- Setting function:
	:___ פונקציה
	command..
	command..
	סוף
	- Calling function: ___ הפעל or just ___
* While loop -
	:___ בעוד
	command..
	command..
	סוף

### Supported data types
* Number
  - Examples: 1.0, 1.2, 1000
* Boolean
  - Supports constants 'true' and 'false' 
  - Example: אמת <> שקר
* Text
  - Delimited by " 
  - Exampe: "שלום"
  
### Supported math functions
* Square root
  - Example: (שורש(64
* Sin
  - Example: (סינוס(0
* Cos
  - Example: (קוסינוס(0
* Tan
  - Example: (טנגנס(0 

### Supported Operations
* '+'	 	- addition  (numbers, string concatenation)
* '-'		- subtraction (numbers)
* '/'		- divide (numbers)
* '*'		- multiply (numbers)
* '=='	- equal (numbers, strings)
* '!='	- not equal to (numbers, strings)
* '<'		- less than (numbers)
* '>'		- greater than (numbers)
* '>='	- greater than or equal to (numbers)
* '<='	- less than or equal to (numbers)
* '&&'	- logical or (booleans)
* '||'	- logical and (booleans)

Supported Platforms
-------------------
The program was only checked on Windows, but should work
on any Java supported platform.

How to build
-------------------
Clone the project locally, open the project folder using IntelliJ IDEA,
and build the artifacts using Build -> Build Artifacts... -> Build (Lang871:jar)


License
-------
Licensed under the GNU General Public License, Version 3.0; everyone is permitted to copy and distribute verbatim copies of this license document, but changing it is not allowed.
