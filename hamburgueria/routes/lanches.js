var express = require('express');
var router = express.Router();

router.get('/', function(req, res) {
	req.db.collection('lanche').find().toArray(function(err, lanches) {
		res.render('lanches', { lanches : lanches });
	});
});

router.get('/all', function(req, res) {
	req.db.collection('lanche').find().toArray(function(err, lanches) {
		res.json('lanches', { lanches : lanches });
	});
});

router.get('/novo-lanche', function(req, res) {
	res.render('novoLanche', {lanche : {}});
});

router.post('/salvar-lanche', function(req, res) {
	var lanche = {
		nome : req.body.nome,
		preco : req.body.preco,
		imagem : req.body.imagem,
		ingredientes : req.body.ingredientes
	};

	req.db.collection('lanche').insert(lanche, function(err, doc) {
		if (err) {
			res.send('There was a problem adding the information to the database.');
		} else {
			res.location('lanches');
			res.redirect('lanches');
		}
	});
});

router.delete('/excluir/:id', function(req, res) {
	req.db.collection('lanche').removeById(req.params.id, function(err, result) {
		res.send((result === 1) ? { msg: '' } : { msg:'error: ' + err });
	});
});

module.exports = router;
