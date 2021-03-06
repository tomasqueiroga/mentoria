var express = require('express');
var path = require('path');
var favicon = require('static-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');

var routes = require('./routes/index');
var lanches = require('./routes/lanches');
var porcoes = require('./routes/porcoes');

var lanchesService = require('./routes/s/lanchesService');
var porcoesService = require('./routes/s/porcoesService');
var bebidasService = require('./routes/s/bebidasService');
var pedidosService = require('./routes/s/pedidosService');
var mongo = require('mongoskin');
var db = mongo.db('mongodb://localhost:27017/hamburgueria', {native_parser:true});

var app = express();

app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(favicon());
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded());
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use(function(req, res, next) {
	req.db = db;
	next();
});
app.use('/', routes);
app.use('/lanches', lanches);
app.use('/porcoes', porcoes);
app.use('/s/lanches', lanchesService);
app.use('/s/porcoes', porcoesService);
app.use('/s/bebidas', bebidasService);
app.use('/s/pedidos', pedidosService);

// / catch 404 and forward to error handler
app.use(function(req, res, next) {
	var err = new Error('Not Found');
	err.status = 404;
	next(err);
});

// / error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
	app.use(function(err, req, res, next) {
		res.status(err.status || 500);
		res.render('error', {
			message : err.message,
			error : err
		});
	});
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
	res.status(err.status || 500);
	res.render('error', {
		message : err.message,
		error : {}
	});
});

module.exports = app;
