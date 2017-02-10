Lang871 
===========

Overview
--------
This is an interpreter for the Lang871 hebrew language. The interpreter is written in pure Java. It can parse any mathematical or logical expression and returns a result. An expression can contain variables that can be supplied before the expression is executed and the result returned. 

### Supported Functions 
* Print - ___ הדפס
* Variable defining - ___ = ___ הגדר
* If conditions - ___ אם ___ אז

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
* +	 	- addition  (numbers, string concatenation)
* -		- subtraction (numbers)
* /		- divide (numbers)
* *		- multiply (numbers)
* ==	- equal (numbers, strings)
* !=	- not equal to (numbers, strings)
* <		- less than (numbers)
* >		- greater than (numbers)
* >=	- greater than or equal to (numbers)
* <=	- less than or equal to (numbers)
* OR	- logical or (booleans)
* AND	- logical and (booleans)

Supported Platforms
-------------------
The supported platform is only Windows right now. 

License
-------
Licensed under the GNU Geneeal Public License, Version 3.0; everyone is permitted to copy and distribute verbatim copies of this license document, but changing it is not allowed.
