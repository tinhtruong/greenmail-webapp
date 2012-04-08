jQuery(document).ready(function($) {
	$("input[type='checkbox'][name='all']").click(function() {
		var checked = $(this).is(":checked");
        if (checked) {
        	$("table.table input[type='checkbox']").attr('checked', 'checked');
            $("table.table input[type='text']").removeAttr('disabled');
        } else {
        	$("table.table input[type='checkbox']").removeAttr('checked');
            $("table.table input[type='text']").attr('disabled', 'disabled');
        }
	});
	$("table.table tbody input[type='checkbox']").click(function() {
		var portInput = $(this).attr('value') + "Port";
        var checked = $(this).is(":checked");
        if (checked) {
        	$("input[type='text'][name='" + portInput + "']").removeAttr('disabled', 'disabled');
        	for each (var other in $("table.table tbody input[type='checkbox']").toArray()) {
        		if (!$(other).is(':checked')) {
        			$("input[type='checkbox'][name='all']").removeAttr('checked');
        			return;
        		}
        	}
        	$("input[type='checkbox'][name='all']").attr('checked', checked);
        } else {
            $("input[type='text'][name='" + portInput + "']").attr('disabled', 'disabled');
        	$("input[type='checkbox'][name='all']").removeAttr('checked');
        }
	});
});
