var express = require('express');
var router = express.Router();

router.get('/', function(req, res) {
	req.db.collection('pedido').find().toArray(function(err, pedidos) {
		res.json(200, pedidos);
	});
});

router.get('/:id', function(req, res) {
	req.db.collection('pedido').findById(req.params.id, function(err, pedido) {
		res.json(200, pedido);
	});
});

router.delete('/:id', function(req, res) {
	req.db.collection('pedido').removeById(req.params.id, function(err, result) {
		res.send(result === 1 ? 200 : 400, result === 1 ? { msg: '' } : { msg : 'error: ' + err });
	});
});

router.post('/', function(req, res) {
	var pedido = {
		bebidas : req.body.bebidas || [],
		porcoes : req.body.porcoes || [],
		lanches : req.body.lanches || [],
		cliente : req.body.cliente,
		endereco : req.body.endereco,
		pagamento : req.body.pagamento
	};

	var erros = [];
	if (!pedido.cliente)
		erros.push('nome-cliente-obrigatorio');
	if (!pedido.endereco)
		erros.push('endereco-obrigatorio');
	if (!pedido.pagamento || !pedido.pagamento.pago || !pedido.pagamento.tipo)
		erros.push('pagamento-obrigatorio');

	var total = 0;
	for (var i = 0; i < pedido.bebidas.length; i++)
		total += parseFloat(pedido.bebidas[i].preco);
	for (var i = 0; i < pedido.porcoes.length; i++)
		total += parseFloat(pedido.porcoes[i].preco);
	for (var i = 0; i < pedido.lanches.length; i++)
		total += parseFloat(pedido.lanches[i].preco);

	pedido.frete = 2.5;
	if (!total)
		erros.push('escolha-algum-produto');
	else if (pedido.pagamento && pedido.pagamento.pago < total + pedido.frete)
		erros.push('valor-total-maior-que-valor-pago');

	if (erros.length > 0) {
		res.json(400, erros);
		return;
	}

	pedido.total = total;
	pedido.status = 'PREPARANDO';
	pedido.pagamento.total = total + pedido.frete;
	pedido.pagamento.troco = pedido.pagamento.pago - pedido.pagamento.total;
	req.db.collection('pedido').insert(pedido, function(err, pedido) {
		res.json(200, pedido);
	});
});

module.exports = router;
