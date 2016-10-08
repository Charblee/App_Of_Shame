var express = require("express");

var app = express();

app.get('/create_group',function (req, res)
{
    
});

app.post('/message/:chat_id/text',function (req, res)
{

});

app.post('/message/:chat_id/pic', function (req, res)
{

});

app.get('/text/:chat_id/:message_id',function (req, res)
{
    console.log("Get text message----------")
    console.log(req.params.chat_id);
    console.log(req.params.message_id);
    res.send("Text returned.");
});

app.get('/pic/:chat_id/:message_id',function (req, res)
{
    console.log("Get pic message-----------");
    console.log(req.params.chat_id);
    console.log(req.params.message_id);
    res.send("Pic returned")
});

app.get('new_messages/:chat_id/:time_since',function (req, res)
{
    res.send("Get messages");
});

app.listen(8080);