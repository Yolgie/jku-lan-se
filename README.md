[![Stories in Ready](https://badge.waffle.io/Yolgie/jku-lan-se.png?label=ready&title=Ready)](https://waffle.io/Yolgie/jku-lan-se)
[![Stories in Progress](https://badge.waffle.io/Yolgie/jku-lan-se.png?label=in%20progress&title=In%20Progress)](https://waffle.io/Yolgie/jku-lan-se)
# Project JKU-LAN Software

## Introduction

This piece of software is developed by the ÖH StV. Informatik JKU Linz in order to support the organization of the JKU-LAN.

## Setup

### Required Software

* Gradle (Version >2.5)
* JDK 8

Note: Some Linux distributions (expecially if based on Debian) have an older Version of Gradle in their repositories. 
If you do not want to manually upgrade to a newer version of Gradle, you can use the Gradle Wrapper. To do so, simply 
run `gradle wrapper` in the 'jku-lan-se' directory. This will create a new `gradlew` executeable within your project directory, 
which can be used instead of gradle. 

### Installation

```
git clone https://github.com/Yolgie/jku-lan-se.git
cd jku-lan-se
gradle bootRun
```

For the most popular IDE's, Eclipse and IntelliJ Idea, there is also a gradle command available in order to make all
dependencies loaded by gradle, available. 

For Eclipse:

```
gradle eclipse
```
For IntelliJ Idea: 
```
gradle idea
```

## Work Convention

We are using the [Github workflow](https://guides.github.com/introduction/flow/):

1.  Keep the master clean
2.  Create a branch/pull-request for every code change. Branch name should start with the issue number, be all english lowercase with dash "-" instead of spaces
3.  Keep your commit messages to this format: `issueNumber-worktag-shortDescription`. 
  + Worktags: `feature`, `test`, `refactoring`, `fix`


