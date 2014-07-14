var express = require('express');
var router = express.Router();

router.get('/', function(req, res) {
	req.db.collection('bebida').find().toArray(function(err, bebidas) {
		res.json(200, bebidas);
	});
});

router.get('/:id', function(req, res) {
	req.db.collection('bebida').findById(req.params.id, function(err, bebida) {
		res.json(200, bebida);
	});
});

router.delete('/:id', function(req, res) {
	req.db.collection('bebida').removeById(req.params.id, function(err, result) {
		res.send(result === 1 ? 200 : 400, result === 1 ? { msg: '' } : { msg : 'error: ' + err });
	});
});

router.post('/', function(req, res) {
	var bebida = {
		nome : req.body.nome,
		preco : req.body.preco,
		tipo : req.body.tipo,
		tamanho : req.body.tamanho
	};

	req.db.collection('bebida').insert(bebida, function(err, bebida) {
		res.json(200, bebida);
	});
});

router.put('/:id', function(req, res) {
	var bebida = {
			nome : req.body.nome,
			preco : req.body.preco,
			tipo : req.body.tipo,
			tamanho : req.body.tamanho
		};

	req.db.collection('bebida').updateById(req.params.id, bebida, function(err, bebida) {
		res.json(200, bebida);
	});
});

module.exports = router;
