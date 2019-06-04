import React from 'react';
import ReactDOM from 'react-dom'
import { createStore } from 'redux'

class WebSite extends React.Component {
    constructor(props) {
        super(props);
        //在state设置两个属性，以便后续通过state对象来对其进行修改
        this.state = {user: '',passwd: ''};
        //绑定挂载事件
        this.handleChangeUser = this.handleChangeUser.bind(this);
        this.handleChangePasswd = this.handleChangePasswd.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }
    handleChangeUser(event) {
      this.setState({user: event.target.value});
    }
    handleChangePasswd(event){
      this.setState({passwd: event.target.value});
    }
    handleSubmit(event) {
        //接下来操作state时上下文对象发生改变，此处拿到操作句柄
        var that = this;
        var jsonObject = "{\"user\":\""+this.state.user+"\",\"passwd\":\""+this.state.passwd+"\",\"apid\":\"00002\"}";
        //ajax请求
        this.serverRequest = $.ajax({
               type: "POST",
               url: "login",
               data: jsonObject,
               dataType: "json",
               contentType:'application/json;charset=utf-8',
               headers:{'Authorization':''},
               async: false,
               success: function(data){
                  that.setState({
                    tokenstring: data[0].accesstoken,
                  });
                  window.open("http://2c2192g178.iask.in/api/index.html");
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
    }

    //卸载React组件时，把多余请求关闭，以免影响其他框架或组件的操作
    componentWillUnmount() {
        this.serverRequest.abort();
    }

    render() {
        return ( 

			<form onSubmit={this.handleSubmit}>
				<div className="form-group has-feedback">
					<input type="text" placeholder="Email" value={this.state.user} onChange={this.handleChangeUser} className="form-control" />
				  <span className="glyphicon glyphicon-envelope form-control-feedback"></span>
				</div>
				<div className="form-group has-feedback">
					<input type="password" placeholder="Password" value={this.state.passwd} onChange={this.handleChangePasswd} className="form-control"/>
				  <span className="glyphicon glyphicon-lock form-control-feedback"></span>
				</div>
				<input type="submit" value="Submit" className="btn btn-primary btn-block btn-flat" />
			</form>

        );
    }
}

export default WebSite;
