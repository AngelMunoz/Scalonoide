# Readme.md

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