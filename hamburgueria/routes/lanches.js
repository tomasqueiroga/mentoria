var express = require('express');
var router = express.Router();

router.get('/', function(req, res) {
	req.db.collection('lanche').find().toArray(function(err, lanches) {
		res.render('lanches', { lanches : lanches });
	});
});

router.get('/editar-lanche/:id', function(req, res) {
	req.db.collection('lanche').findById(req.params.id, function(err, lanche) {
		res.render('novoLanche', { lanche : lanche });
	});
});

router.get('/novo-lanche', function(req, res) {
	res.render('novoLanche', {lanche : {}});
});

module.exports = router;
