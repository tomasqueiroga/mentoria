var express = require('express');
var router = express.Router();

router.get('/', function(req, res) {
	req.db.collection('porcao').find().toArray(function(err, porcoes) {
		res.render('porcoes', {porcoes : porcoes});
	});
});

router.get('/editar-porcao/:id', function(req, res) {
	req.db.collection('porcao').findById(req.params.id, function(err, porcao) {
		res.render('novaPorcao', {porcao : porcao});
	});
});

router.get('/nova-porcao', function(req, res) {
	res.render('novaPorcao', {porcao : {}});
});

module.exports = router;
