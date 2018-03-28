/**
 * Created by WYX on 2018/3/9.
 */
var dev_idents = [];
for (var i = 0; i < data.length; i++) {
    for (var j in data[i]) {
        if (j == "text") {
            continue;
        }
        dev_idents.push(data[i][j]);

    }
}
alert(dev_idents);
var dev_names=[];
for (var dev_ident in dev_idents) {
    var idents = dev_ident.split('&');
    if (idents[0].indexOf("N") && idents[1].indexOf("T")) {
        dev_ident_map[idents[0]] = idents[1];
        dev_names.push(Number(idents[0]));
    } else if (idents[0].indexOf("T") && idents[1].indexOf("N")) {
        dev_ident_map[idents[1]] = idents[0];
        dev_names.push(Number(idents[1]));
    } else {
    }
}
dev_ident_map