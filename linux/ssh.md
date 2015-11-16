## Working with git

* Start the ssh-agent with =>
`$ ssh-agent`

* Add the key with =>
`$ ssh-add ~/trunk/ssh_keys/personal/id_rsa`  
Enter the password when prompted. You will get a msg similar to following on success =>
`Identity added: /home/raj/trunk/ssh_keys/personal/id_rsa`

* You can now perform git operations using the above key.

* To list the available keys =>
```
$ ssh-add -l
2048 SHA256:TonSZyR0vtlTGIiQzdcxjI4UYar3CHXnFJBbtyyWilw /home/raj/trunk/ssh_keys/personal/id_rsa (RSA)
```
