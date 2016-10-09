var express = require("express");
var body_parser = require("body-parser");
var mysql = require("mysql");

var app = express();

app.use(body_parser());

var connection = mysql.createConnection({
    host     : 'localhost',
    user     : 'shame',
    password : 'passwd',
    database : 'shame_app',
    multipleStatements:true
});
connection.connect();

app.get('/create_group',function (req, res)
{
    connection.query('INSERT INTO chats VALUES();',function (err, out)
    {
        if (err)
        {
            console.log(err);
        }
        else
        {
            res.send({chat_id:out.insertId});
        }
    })
});

app.post('/message/:chat_id/text',function (req, res)
{
    console.log(req.body);
    connection.query('INSERT INTO texts (chat_id, text) SELECT ?, ? WHERE EXISTS(SELECT 1 FROM chats WHERE chat_id=?);'
        ,[req.params.chat_id, req.body.msg, req.params.chat_id], function (err, out)
        {
            if (err)
            {
                console.log(err);
                res.send("ERROR");
            }
            else
            {
                console.log(out);
                if (out.affectedRows <= 0)
                {
                    res.send("Not valid.");
                }
                res.sendStatus(200);
            }
        });
});

app.post('/message/:chat_id/pic', function (req, res)
{
    console.log(req.body);
    res.send("Picture posted");
});

app.get('/text/:chat_id/:message_id',function (req, res)
{
    console.log("Get text message----------");
    console.log(req.params.chat_id);
    console.log(req.params.message_id);

    connection.query('SELECT * FROM texts WHERE chat_id=? and message_id=?',
        [req.params.chat_id, req.params.message_id], function (err, out)
    {
        if (err)
        {
            console.log(err);
            res.sendStatus(500);
        }
        else
        {
            console.log(out[0]);
            res.send(out[0]);
        }
    });
});

app.get('/pic/:chat_id/:message_id',function (req, res)
{
    console.log("Get pic message-----------");
    console.log(req.params.chat_id);
    console.log(req.params.message_id);
    res.send("Pic returned")
});

app.post('/new_messages/:chat_id',function (req, res)
{
    console.log(req.body);
    console.log(req.body.since);

    connection.query('SELECT message_id FROM texts WHERE chat_id=? AND post_time > FROM_UNIXTIME(UNIX_TIMESTAMP(?));',
        [req.params.chat_id, req.body.since],function (err, out)
        {
            if (err)
            {
                console.log(err);
                res.sendStatus(500);
            }
            else
            {
                console.log(out);
                res.send(out);
            }
        });
});

app.listen(8080);