var express = require('express');
var router = express.Router();

router.get('/', function(req, res) {
	req.db.collection('lanche').find().toArray(function(err, lanches) {
		res.json(200, lanches);
	});
});

router.get('/:id', function(req, res) {
	req.db.collection('lanche').findById(req.params.id, function(err, lanche) {
		res.json(200, lanche);
	});
});

router.delete('/:id', function(req, res) {
	req.db.collection('lanche').removeById(req.params.id, function(err, result) {
		res.send(result === 1 ? 200 : 400, result === 1 ? { msg: '' } : { msg : 'error: ' + err });
	});
});

router.post('/', function(req, res) {
	var lanche = {
		nome : req.body.nome,
		preco : req.body.preco,
		imagem : req.body.imagem,
		ingredientes : req.body.ingredientes
	};

	req.db.collection('lanche').insert(lanche, function(err, lanche) {
		res.json(200, lanche);
	});
});

router.put('/:id', function(req, res) {
	var lanche = {
		nome : req.body.nome,
		preco : req.body.preco,
		imagem : req.body.imagem,
		ingredientes : req.body.ingredientes
	};

	req.db.collection('lanche').updateById(req.params.id, lanche, function(err, lanche) {
		res.json(200, lanche);
	});
});

module.exports = router;
