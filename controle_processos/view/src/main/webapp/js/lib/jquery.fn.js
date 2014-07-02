(function($) {

	$.fn.toJson = function() {
		var json = {};

		$(this)
			.find('input[type="text"], input[type="checkbox"]:checked, input[type="radio"]:checked, textarea, select')
			.filter('[name]:visible:not([disabled])')
			.each(function() {
				json[$(this).attr('name')] = $(this).val();
			});
		return json;
	};

	$.fn.message = function(opts) {
		if (!$.isPlainObject(opts))
			opts = {text : opts};

		if (!opts.text)
			return $(this);

		var messageDiv = $('<div class="message ' + (opts.type ? opts.type : 'success') + '" data-no-animation="true">' + i18n(opts.text) + '</div>').hide().click(function() {
			$(this).hide('fast');
		});

		$(this)
			.find('.message').hide('fast', function() {
				$(this).remove();
			}).end()
			.prepend(messageDiv);

		messageDiv.show('fast');
		if (!opts.noScroll)
			$(window).scrollTop($(this).find('.message').position().top - $('.header').outerHeight(), 'fast');

		return this;
	};

})(window.jQuery);

