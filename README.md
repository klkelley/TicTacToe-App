# tictactoe-app [![Build Status](https://travis-ci.org/klkelley/tictactoe-app.svg?branch=master)](https://travis-ci.org/klkelley/tictactoe-app)


[react-tictactoe](https://github.com/klkelley/react-tictactoe) app served on the [http-java](https://github.com/klkelley/http-java) server. 

## Visit on the web 

[ttt.karakelley.rocks](http://ttt.karakelley.rocks) 

### Build Project 
```$xslt
$ gradle build
```

### Run Project
If you are running the project locally you will also want to spin up the [TicTacToe API](https://github.com/klkelley/tictactoe-web-app)
and serve it on port 5000. 

```$xslt
$ java -jar build/libs/react.jar -p <$PORT> -d /lib/build/
```

### Run Tests
```$xslt
$ gradle test 
```
