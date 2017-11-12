# Merge a repository in another while maintaining git history of both.
Suppose you were working on a small experimental project or POC `repoA` in its own Git repo. Now, you need to bring it under a bigger umbrella of another repository of related projects/modules/experiments inside `repo-B`. 
Follow the below steps:
```
cd path/to/repo-b
git remote add repo-a path/to/repo-a
git fetch repo-a
git merge --allow-unrelated-histories repo-a/master # or whichever branch you want to merge
git remote remove repo-a

# To move the contents to their own subdirectory
mkdir repoA
ls -a| grep -v repoA| xargs -I {} git mv {} repoA

# Git commit to finalize
```
**Note**: If both projects have a lot of files in their root directory, it's kind of a pain to sift through all of them and move where necessary. Instead, make sure the root level just has distinct project folders only.
___
