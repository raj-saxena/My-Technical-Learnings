> Bash
 * $? --> returns the status of last command.
 For eg : 
 ```bash
	true
	echo $? # echoes 0
	false
	echo $? # echoes 1
 ```

 * $@ --> is all the arguments passed to script 
 For eg : 
	For instance, if you call ./someScript.sh foo bar then $@ will be equal to foo bar.

 * $# --> gives the _number of arguments_ that are passed to shell
