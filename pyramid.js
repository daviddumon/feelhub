for(var i = 0; i < 100000; i++) {
db.opinions.insert({text:'a'+i,feelings:[{feeling:'bad',sujet:'A'},{feeling:'bad',sujet:'B'}]});
db.opinions.insert({text:'b'+i,feelings:[{feeling:'bad',sujet:'A'},{feeling:'good',sujet:'D'}]});
db.opinions.insert({text:'c'+i,feelings:[{feeling:'bad',sujet:'C'},{feeling:'neutral',sujet:'B'}]});
db.opinions.insert({text:'d'+i,feelings:[{feeling:'good',sujet:'A'},{feeling:'good',sujet:'C'}]});
db.opinions.insert({text:'e'+i,feelings:[{feeling:'neutral',sujet:'A'},{feeling:'good',sujet:'A'}]});
}
