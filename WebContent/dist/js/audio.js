$(function() {
    // 播放器
    var Player = {
        // 歌曲路径
        path : 'http://2c2192g178.iask.in/video/',
        // 歌曲数据
        data : new Array(),
        // 当前播放歌曲的 索引
        currentIndex : -1,
        //  播放器元素jquery对象
        $audio : $('audio'),
        // 歌曲列表
        $mList : $('#m-list'),
        //正在播放的歌曲
        $rmusic : $('#rmusic'),
        // 初始化 数据
        init : function() {
            var url=document.location.search;
		    var params = url.split("&");
            var val = params[0].split("=")[1];
            alert(val);
            // 数据一般来自服务器端,通过ajax 加载数据,这里是模拟
            $.ajax({
               type: "POST",
               url: "video",
               data: "appid=u00001&videocd="+val,
               dataType: "json",
               headers:{'Authorization':''},
               async: false,
               success: function(data){
               	    var obj=data[0].data;
               	    for(var i in obj){
               	    	  Player.data[i] = obj[i].Location+"/"+obj[i].AudioName;
                    }
                    Player.data.sort();
               },
	           error:function(XMLHttpRequest, textStatus, errorThrown){
		           // 状态码
	               console.log(XMLHttpRequest.status);
	               // 状态
	               console.log(XMLHttpRequest.readyState);
	               // 错误信息   
	               console.log(textStatus);
	           }
            })
            //Player.data = ['1431914743.mp3', '刘德华-那一天.mp3'];
 
            // 一般用模板引擎,把数据 与 模板 转换为 视图,来显示,这里是模拟
            var mhtml = '';
            var len = Player.data.length;
            for (var i = 0; i < len; i++) {
                mhtml += '<li><a index="' + i + '">' + Player.data[i] + '</a></li>';
            }
            Player.$mList.html(mhtml);
        },
        // 就绪
        ready : function() {
            // 控制
            Player.audio = Player.$audio.get(0);
            $('#ctrl-area').on('click', 'button', function() {
                Player.$rmusic.html(Player.data[Player.currentIndex]);
            });
            // 播放
            $('#btn-play').click(function() {
                Player.audio.play();
                if (Player.currentIndex == -1) {
                    $('#btn-next').click();
                }
            });
            // 暂停
            $('#btn-pause').click(function() {
                Player.audio.pause();
            });
            // 下一曲
            $('#btn-next').click(function() {
                if (Player.currentIndex == -1) {
                    Player.currentIndex = 0;
                } else if (Player.currentIndex == (Player.data.length - 1)) {
                    Player.currentIndex = 0;
                } else {
                    Player.currentIndex++;
                }
                console.log("Player.currentIndex : " + Player.currentIndex);
                Player.audio.src = Player.path + Player.data[Player.currentIndex];
                Player.audio.play();
            });
            // 上一曲
            $('#btn-pre').click(function() {
                if (Player.currentIndex == -1) {
                    Player.currentIndex = 0;
                } else if (Player.currentIndex == 0) {
                    Player.currentIndex = (Player.data.length - 1);
                } else {
                    Player.currentIndex--;
                }
                Player.audio.src = Player.path + Player.data[Player.currentIndex];
                Player.audio.play();
            });
            // 单曲循环
            $('#btn-loop').click(function() {
                console.log("Player.currentIndex :", Player.currentIndex);
                Player.audio.onended = function() {
                    Player.audio.load();
                    Player.audio.play();
                };
            });
            // 顺序播放
            $('#btn-order').click(function() {
                console.log("Player.currentIndex :", Player.currentIndex);
                Player.audio.onended = function() {
                    $('#btn-next').click();
                };
            });
            // 随机播放
            $('#btn-random').click(function() {
                Player.audio.onended = function() {
                    var i = parseInt((Player.data.length - 1) * Math.random());
                    playByMe(i);
                };
            });
            // 播放指定歌曲
            function playByMe(i) {
                console.log("index:", i);
                Player.audio.src = Player.path + Player.data[i];
                Player.audio.play();
                Player.currentIndex = i;
                Player.$rmusic.html(Player.data[Player.currentIndex]);
            }
            // 歌曲被点击
            $('#m-list a').click(function() {
                playByMe($(this).attr('index'));
            });
        }
    };
    Player.init();
    Player.ready();
});