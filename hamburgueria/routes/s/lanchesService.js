var express = require('express');
var router = express.Router();

router.get('/', function(req, res) {
	req.db.collection('lanche').find().toArray(function(err, lanches) {
		res.json('lanches', lanches);
	});
});

router.get('/:id', function(req, res) {
	req.db.collection('lanche').findById(req.params.id, function(err, lanche) {
		res.json('lanche', lanche);
	});
});

router.delete('/:id', function(req, res) {
	req.db.collection('lanche').removeById(req.params.id, function(err, result) {
		res.send(result === 1 ? { msg: '' } : { msg:'error: ' + err });
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
		res.json(lanche);
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
		res.json(lanche);
	});
});

module.exports = router;
