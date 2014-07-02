(function($) {

	var doTCache = {};

	var render = function(templateObj, selector, context, opts) {
		context = $.extend({user : $('body').data('user')}, context);
		var $template = $(templateObj.templateFunc(context));
		$template.find('hidden').hide();
		$template.find('input[type="file"]').inputFile();

		var container = $(selector); 
		if (!opts.append)
			container.empty();
		container.append($template);

		templateObj.script.each(function() {
			eval($(this).html());
		});

		if (typeof opts.complete == 'function')
			opts.complete();
	};

	$.load = function(selector, context, opts) {
		var opts = opts || {};
		var componentName = opts.componentName || $(selector).data('component');
		var componentPath = opts.componentPath || $(selector).data('path') || componentName;
		if (doTCache[componentPath + componentName])
			render(doTCache[componentPath + componentName], selector, context, opts);
		else
			$.ajax({
				type: 'GET',
				url: 'component/' + componentPath + '/' + componentName + '.xml?_' + new Date().getTime(),
				dataType: 'text',
				success: function(template) {
					doTCache[componentPath + componentName] = {
						templateFunc : doT.template(getHTML($.parseHTML(template)[0].children[0])),
						script : $(template).children('script')
					}
					render(doTCache[componentPath + componentName], selector, context, opts);
				}
			});
	};

	var getHTML = function(e) {
		var html = [];

		for (var i = 0; i < e.childNodes.length; i++)
			html.push(getChildrenHTML(e.childNodes[i]));

		return html.join('');
	};

	var getChildrenHTML = function(e) {
		var html = [];

		if (e.nodeType == document.TEXT_NODE || e.nodeType == document.COMMENT_NODE) {
			html.push(e.data);
		}
		else if (e.nodeType == document.ELEMENT_NODE) {
			html.push('<', e.nodeName);
			var attrs = e.attributes;
			for (var i = 0; i < attrs.length; i++) {
				var attr = attrs.item(i);
				html.push(' ', attr.name, '="', attr.value, '"');
			}

			if (e.nodeName != 'br') {
				html.push(">");
				html.push(getHTML(e));
				html.push("</", e.nodeName, ">");
			} else
				html.push(" />");
		} else
			console.info(e.nodeType, e.nodeName);

		return html.join('');
	};

	$.ajaxSetup({
		statusCode : {
			400 : function(message) {
				if (!message.responseText)
					return;
				message = eval(message.responseText);
				if ($.isArray(message))
					for (var i in message)
						$('.main-content').message({text : message[i], type : 'error'});
				else
					$('.main-content').message({text : message, type : 'error'});
			},
			401 : function() {
				$('.main-content').message({text : 'sign-in', type : 'error'});
			},
			403 : function() {
				$('.main-content').message({text : i18n('unauthorized'), type : 'error'});
			},
			404 : function() {
			}
		},
		error : function(error) {
			if (error.statusCode > 404 && error.responseText)
				$('.main-content').message({text : error.responseText, type : 'error'});
		}
	});

	$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
		if ((options.type === 'POST' || options.type === 'PUT') && options.form)
			options.data = options.form.attr('name') + '=' + JSON.stringify(options.form.toJson()) + (options.data ? '&' + options.data : '');
	});
})(window.jQuery);
 