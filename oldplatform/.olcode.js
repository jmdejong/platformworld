var World=Class.extend({
	sprites:[],
	map:[],
	data:[],
	visible:[],
	vx:0,
	vy:0,
	vd:0,
	loaded:false,
	grid:new Grid(),
	drawOffSet:1,
	get:function(x,y){return this.grid.get(x,y)},
	collision:"complex",
	getMap:function(x,y){
		return this.map&&this.map[x]&&this.map[x][y];
	},
	collidesWith:function(obj){
		if (obj.collision=="rectangle"){
			var x,y;
			for (x=Math.floor((obj.x+obj.box.l));x<=(obj.x+obj.box.r);x++){
				for (y=Math.floor((obj.y+obj.box.t));y<(obj.y+obj.box.b);y++){
					if (this.solid(x,y)){
						if ((this.solid(x,y)==2)&&((obj.y+obj.box.b-y)>0.5)){
							continue;
						}
						return this.solid(x,y);
					}
				}
			}
			return false;
		}
	},
	solid:function(x,y){
		return this.map&&this.map[x]&&this.map[x][y]&&this.map[x][y].solid;
	},
	drawMap:function(drawing,level,data){
		var x,y;
		var w=Math.min(this.width,Math.floor(data.r+this.drawOffSet));
		var h=Math.min(this.height,Math.floor(data.b+this.drawOffSet));
		for (y=Math.max(Math.floor(data.t-this.drawOffSet),0);y<h;y++){
			for (x=Math.max(Math.floor(data.l-this.drawOffSet),0);x<w;x++){
				var b=this.getMap(x,y);
				if (!b)
					continue;
				if (b.hidden&&this.visible[b.hidden.id])
					b=b.hidden;
				var t=b["tex"+level];
				if (!t)
					continue;
				for (var s=0;s<t.length;s++){
					if (t[s]!==undefined){
						drawing.drawSprite(t[s],x*data.scl,y*data.scl);
					}
				}
			}
		}
	},
	setTextures:function(x,y,hidden){
		var w=this.map&&this.map[x]&&this.map[x][y];
		if (!(w&&w.data)){
			return;
		}
		b=w.data;
		if (hidden&&b.hidden&&hidden==b.hidden.id){
			b=b.hidden;
		}
		var around={"left":[-1,0],"bottom":[0,1],"right":[1,0],"top":[0,-1]};
		var a={}
		for (var i=1;i<20;i++){
			if (b["tex"+i]){
				a["tex"+i]=b["tex"+i].concat();
			}
		}
		for (var n in around){
			var z=this.data[this.get(x+around[n][0],y+around[n][1])];
			if (hidden&&z&&z.hidden&&hidden==z.hidden.id)
				z=z.hidden
			if (b[n]&&(!z||!((z.filled===undefined)?z.solid:z.filled))){
				for (var m in b[n]){
					a[m]=b[n][m].concat(a[m]);
				}
			}
		}
		
		for (var i=1;i<20;i++){
			w["tex"+i]=[];
			if (!a["tex"+i]) continue;
			for (var j=0;j<a["tex"+i].length;j++)
				w["tex"+i][j]=this.sprites[a["tex"+i][j]];
		}
		w.hiding=hidden;
	},
	ev_draw0:function(data){
		if (!this.loaded){
			return;
		}
		for (var x=-this.vd;x<this.vd;x++){
			for (var y=-this.vd;y<this.vd;y++){
				if (Math.abs(x)+Math.abs(y)>this.vd||!this.map[this.vx+x][this.vy+y].hiding)
					continue;
				this.setTextures(this.vx+x,this.vy+y,id);
			}
		}
		this.vx=Math.floor(data.cx);
		this.vy=Math.floor(data.cy);
		var vb=this.map[this.vx][this.vy];
		if (vb.hidden){
			var id=vb.hidden.id;
			this.vd=vb.hidden.distance;
			if (this.vd){
				for (var x=-this.vd;x<this.vd;x++){
					for (var y=-this.vd;y<this.vd;y++){
						if (Math.abs(x)+Math.abs(y)>this.vd||this.map[this.vx+x][this.vy+y].hiding==id)
							continue;
						this.setTextures(this.vx+x,this.vy+y,id);
					}
				}
			}
		}else{
			this.vd=0;
		}
	},
	init:function(fileName,data,sprites,events,layer,objects,collisions,newData,game){
		this.data=data;
		this.newData=newData;
		this.sprites=sprites;
		var thi=this;
		for (var i=1;i<20;i++){
			this["ev_draw"+i]=(function(level){
				return function(data){thi.drawMap(data.drawing,level,data);};
			})(i);
		}
		this.grid.fromImageFile(fileName,function(){
			thi.width=thi.grid.width;
			thi.height=thi.grid.height;
			thi.grid.for(function(x,y,val){
				if (x>=thi.map.length){
					thi.map[x]=[];
				}
				var b=thi.data[val];
				var c=thi.newData[val];
				if (c){
					for (var i=0;i<c.length;i++){
						var a=new window[c[i]](x,y);
						a.setSprite(sprites);
						events.addObj(a);
						layer.addObj(a);
						objects.push(a);
					}
				}/*/
				if (!b) {
					thi.map[x][y]={solid:0,data:""};
					return;
				}
				thi.map[x][y]=new Block(x,y);
				thi.map[x][y].solid=b.solid;
				thi.map[x][y].data=b;
				events.addObj(thi.map[x][y])
				if (b.hidden){
					thi.map[x][y].hidden={id:b.hidden.id,distance:b.hidden.distance};
				}/**/
			});
			thi.grid.for(function(x,y,val){
				thi.setTextures(x,y);
			});
			events.dispatch("loaded",thi);
			events.dispatch("startCollisions",collisions);
			events.dispatch("getStartCollisions",collisions);
			thi.loaded=true;
			game.step=setInterval(update,30,game);
		});
		
	}
});

var map=new Grid({
		scl:b.scale,
		sprites:textures.sprites,
		map:[],
		data:data,
		collision:"complex",
		getMap:function(x,y){
			return this.map&&this.map[x]&&this.map[x][y];
		},
		collidesWith:function(obj){
			if (obj.collision=="rectangle"){
				var x,y;
				for (x=Math.floor((obj.x+obj.box.l)/this.scl);x<=(obj.x+obj.box.r)/this.scl;x++){
					for (y=Math.floor((obj.y+obj.box.t)/this.scl);y<(obj.y+obj.box.b)/this.scl;y++){
						if (this.solid(x,y)){
							return true;
						}
					}
				}
				return false;
			}
		},
		solid:function(x,y){
			return !this.map||!this.map[x]||!this.map[x][y]||this.map[x][y].solid;
		},
		drawMap:function(drawing,level){
			var x,y;
			var w=Math.min(this.width,Math.floor((drawing.view.w+drawing.view.x)/this.scl+1));
			var h=Math.min(this.height,Math.floor((drawing.view.h+drawing.view.y)/this.scl+1));
			for (y=Math.max(Math.floor(drawing.view.y/this.scl),0);y<h;y++){
				for (x=Math.max(Math.floor(drawing.view.x/this.scl),0);x<w;x++){
					var b=this.getMap(x,y);
					if (!b) continue;
					var t=b["tex"+level];
					if (!t) continue;
					for (var s=0;s<t.length;s++){
						if (t[s]!==undefined){
							drawing.drawSprite(t[s],x*this.scl,y*this.scl);
						}
					}
				}
			}
		}
	});
	for (var i=1;i<20;i++){
		map["ev_draw"+i]=(function(level){
			return function(drawing){map.drawMap(drawing,level);};
		})(i);
	}
	map.fromImageFile("testmap2.png",function(){
		map.for(function(x,y,val){
			if (x>=map.map.length)
				map.map[x]=[];
			var b=map.data[val];
			if (!b) {
				map.map[x][y]={solid:0};
				return;
			}
			map.map[x][y]={solid:b.solid};
			var around={"left":[-1,0],"bottom":[0,1],"right":[1,0],"top":[0,-1]};
			if (b){
				var a={}
				for (var i=1;i<20;i++){
					if (b["tex"+i]){
						a["tex"+i]=b["tex"+i].concat();
					}
				}
				for (var n in around){
					var z=map.data[map.get(x+around[n][0],y+around[n][1])];
					if (b[n]&&(!z||!z.solid)){
						for (var m in b[n]){
							a[m]=b[n][m].concat(a[m]);
						}
					}
				}
						
				for (var i=1;i<20;i++){
					if (!a["tex"+i]) continue;
					map.map[x][y]["tex"+i]=[]
					for (var j=0;j<a["tex"+i].length;j++)
						map.map[x][y]["tex"+i][j]=map.sprites[a["tex"+i][j]];
				}
			}
		});
	});


function View(){
	this.x=0;
	this.y=0;
	this.width=1;
	this.height=1;
	this.zoom=1;
	
	this.locate=function(x,y){
		this.x=x;
		this.y=y;
	};
	this.move=function(x,y){
		this.x+=x;
		this.y+=y;
	};
	this.resize=function(w,h){
		this.width=w;
		this.height=h;
	};
}


function main(){

	var b=JSON.parse(loadSync("blocks.json"));
	var texdata=JSON.parse(loadSync(b.textures));
	var textures=new Textures(texdata);
	var data={};
	for (var x in b.blocks){
		if (isNaN(Number(x))) continue;
		data[Number(x)]=b.blocks[x];
	}

	var newData={};
	for (var x in b.blocks2){
		if (isNaN(Number(x))) continue;
		newData[Number(x)]=b.blocks2[x];
	}



	var layers=[];
	
	for (var i=0;i<b.background.length;i++){
		layers.push(new DrawLayer(b.background[i].depth,b.background[i].scale,1));
		var d=[];
		for (var x in b.background[i].sprites){
			d[Number(x)]=textures.sprites[b.background[i].sprites[x]];
		}
		var bg=new Background(b.background[i].map,d)
		layers[i].addObj(bg);
	}
	
	layers.push(new DrawLayer(1,32,20));

	var objects=[];
	game={drawing:output,events:time,layers:layers,collisions:collisions};
	var map=new World(b.main.map,data,textures.sprites,time,layers[layers.length-1],objects,collisions,newData,game);
	game.world=map;
	for (var x in b.objects){
		for (var y=0;y<b.objects[x].length;y++){
			var a=new window[x](b.objects[x][y].x,b.objects[x][y].y);
			a.setSprite(textures.sprites);
			if (b.objects[x][y].controller){
				a.controller=new window[b.objects[x][y].controller](a);
				time.addObj(a.controller);
			}
			//for (var z in b.objects[x][y])
				//a[z]=b.objects[x][y][z];
			time.addObj(a);
			layers[layers.length-1].addObj(a);
			objects.push(a);
		}
	}
	layers[layers.length-1].field=collisions;
	//time.addObj(map);
	//layers[layers.length-1].addObj(map);
	time.addObj(collisions);
}



	"main":{
		"map":"testmap2.png",
		"depth":1,
		"scale":32
	},
	"background":[
		{
			"map":"clouds.png",
			"depth":4,
			"scale":32,
			"sprites":{
				"0x606060":"cloud"
			}
		},
		{
			"map":"background.png",
			"depth":1.5,
			"scale":32,
			"sprites":{
				"0x00ff00":"grass",
				"0x606060":"stone"
			}
		}
	],


"blocks2":{
		"0x606060":[
			"Stone"
		],
		"0x705000":[
			"Ground"
		],
		"0x606000":[
			"Grass"
		],
		"0xd0d0d0":[
			"Block"
		],
		"0x603000":[
			"TreeStem"
		],
		"0x604000":[
			"TreeBranch"
		],
		"0x008000":[
			"Leaves"
		],
		"0x308030":[
			"Leaves"
		],
		"0xff0000":[
			"Fire"
		],
		"0x00ff00":[
			"Bush"
		]
	},
	"blocks":{
		"0x606060":{
			"tex13":["black"],
			"top":{
				"tex14":["stoneTop"]
			},"bottom":{
				"tex14":["stoneBottom"]
			},"left":{
				"tex14":["stoneLeft"]
			},"right":{
				"tex14":["stoneRight"]
			},
			"solid":1
		},
		"0x606070":{
			"tex13":["black"],
			"top":{
				"tex14":["stoneTop"]
			},"bottom":{
				"tex14":["stoneBottom"]
			},"left":{
				"tex14":["stoneLeft"]
			},"right":{
				"tex14":["stoneRight"]
			},
			"hidden":{
				"id":1,
				"distance":6,
				"filled":0
			},
			"solid":0,
			"filled":1
		},
		"0x606068":{
			"tex13":["black"],
			"top":{
				"tex14":["stoneTop"]
			},"bottom":{
				"tex14":["stoneBottom"]
			},"left":{
				"tex14":["stoneLeft"]
			},"right":{
				"tex14":["stoneRight"]
			},
			"hidden":{
				"id":2,
				"distance":6,
				"filled":0
			},
			"solid":0,
			"filled":1
		},
		"0x705000":{
			"tex13":["black"],
			"left":{
				"tex14":["groundLeft"]
			},
			"bottom":{
				"tex14":["groundBottom"]
			},
			"right":{
				"tex14":["groundRight"]
			},
			"top":{
				"tex14":["groundTop"]
			},
			"solid":1
		},
		"0x606000":{
			"tex13":["black"],
			"left":{
				"tex14":["groundLeft"]
			},
			"bottom":{
				"tex14":["groundBottom"]
			},
			"right":{
				"tex14":["groundRight"]
			},
			"top":{
				"tex14":["groundTop"],
				"tex15":["grassTop"]
			},
			"solid":1
		},
		"0x606010":{
			"tex13":["black"],
			"left":{
				"tex14":["groundLeft"]
			},
			"bottom":{
				"tex14":["groundBottom"]
			},
			"right":{
				"tex14":["groundRight"]
			},
			"top":{
				"tex14":["groundTop"],
				"tex15":["grassTop"]
			},
			"hidden":{
				"id":1,
				"distance":6,
				"filled":0
			},	
			"solid":0,
			"filled":1
		},
		"0xff00f0":{
			"tex13":["black"],
			"left":{
				"tex14":["groundLeft"],
				"tex15":["gnomeDoorLeft"]
			},
			"bottom":{
				"tex14":["groundBottom"]
			},
			"right":{
				"tex14":["groundRight"],
				"tex15":["gnomeDoorRight"]
			},
			"top":{
				"tex14":["groundTop"],
				"tex15":["grassTop"]
			},
			"solid":1
		},
		
		"0x603000":{
			"tex8":["treeStem"],
			"solid":0
		},
		"0x604000":{
			"tex8":["treeBranch"],
			"solid":2
		},
		"0x008000":{
			"tex11":["leaves"],
			"solid":0
		},
		"0x308030":{
			"tex9":["leaves"],
			"solid":0
		},
		
		"0x909090":{
			"tex15":["stone"],
			"solid":1
		},
		"0xff0000":{
			"tex16":["fire"],
			"solid":0
		},
		"0x00ff00":{
			"solid":0,
			"tex16":["bush"]
		},
		
		"0xd0d0d0":{
			"filled":0,
			"solid":1
		},
		"0xffff00":{
			"obj":"Player"
		}
		
	},


