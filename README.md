# Scalonoide
this is a small replica from one of the first projects I've ever made [dbJyUpdater](https://github.com/AngelMunoz/dbJyUpdater)
that was a project I made for school where I picked a CSV file from disk and updated a MySQL database using Jython and Java Swing

This time I decided to use Scala as the Development language for Learning Purposes
for being a replica/proof of concept of a Language I don't really know that much I feel Happy with the results
it means Scala isn't as Scary as it may seem, feels really good and quite familiar, of course this project is a toy
and there are no best practices around.

recommended `.vscode/settings.json`

```json5
{
  "metals.javaHome": "where is your jdk",
  "files.exclude": {
    "**/.git": true,
    "**/.svn": true,
    "**/.hg": true,
    "**/CVS": true,
    "**/.DS_Store": true,
    // prevent build tools from cluttering
    "**/.bloop": true,
    "**/.idea": true,
    "**/.metals": true,
    "**/target": true
  }
}
```

# Run Instructions
You need to have a mongo database running on your localhost
or specify when running as a parameter

#### Use SBT
sbt
```
sbt:Scalonoide> run
```

run with a different Database
```
sbt:Scalonoide> run db_url=mongodb://mydatabaseurl
```

you can run the program with different options
```
# separator[CHAR] example: `separator;` uses ";" as separator for the arguments 
# title=yourtitle
# env=production
# db_url=mongodb://yoururl
# db_name=scalonoidedb

sbt:Scalonoide> run separator~ title~yourtitle env~production db_url~mongodb://yoururl db_name~scalonoidedb
```