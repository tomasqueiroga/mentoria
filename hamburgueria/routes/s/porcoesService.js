var express = require('express');
var router = express.Router();

router.get('/', function(req, res) {
	req.db.collection('porcao').find().toArray(function(err, porcoes) {
		res.json('porcoes', porcoes);
	});
});

router.get('/:id', function(req, res) {
	req.db.collection('porcao').findById(req.params.id, function(err, porcao) {
		res.json('porcao', porcao);
	});
});

router.delete('/:id', function(req, res) {
	req.db.collection('porcao').removeById(req.params.id, function(err, result) {
		res.send(result === 1 ? { msg: '' } : { msg : 'error: ' + err });
	});
});

router.post('/', function(req, res) {
	var porcao = {
		nome : req.body.nome,
		preco : req.body.preco,
		imagem : req.body.imagem,
		ingredientes : req.body.ingredientes
	};

	req.db.collection('porcao').insert(porcao, function(err, porcao) {
		res.json(porcao);
	});
});

router.put('/:id', function(req, res) {
	var porcao = {
		nome : req.body.nome,
		preco : req.body.preco,
		imagem : req.body.imagem,
		ingredientes : req.body.ingredientes
	};

	req.db.collection('porcao').updateById(req.params.id, porcao, function(err, porcao) {
		res.json(porcao);
	});
});

module.exports = router;
