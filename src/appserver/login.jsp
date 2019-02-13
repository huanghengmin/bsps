<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html;charset=utf-8" import="com.hzih.bsps.web.SiteContext"%>
<%@include file="/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>BS 移动应用代理中心</title>

<script language="JavaScript">
	function reloadVerifyCode() {
		document.getElementById('verifyCodeImg').src = "<c:url value="/RandomCodeCtrl"/>"+"?tmp="+RndNum(9000) ;
	}
	
	function checkForm(){
		var form = document.forms.loginForm;
		if(form.name.value==null||form.name.value.length==0){
			alert("用户名不能为空！");
			return false;
		}
				var password = form.pwd.value;
				var re = /^[0-9a-zA-Z!$#%@^&*()~_+]{8,20}$/;
				if(!re.test(password)){
					alert("密码必须为大写字母、小写字母、数字和特殊字符的组合，且不少于8位！");
					return false;
				}
				//re = /([0-9].*([a-zA-Z].*[!$#%@^&*()~_+]|[!$#%@^&*()~_+].*[a-zA-Z])|[a-zA-Z].*([0-9].*[!$#%@^&*()~_+]|[!$#%@^&*()~_+].*[0-9])|[!$#%@^&*()~_+].*([0-9].*[a-zA-Z]|[a-zA-Z].*[0-9]))/;
				//if(!re.test(password)){
					//alert("密码必须为大写字母、小写字母、数字和特殊字符的组合，且不少于8位！");
					//return false;
				//}			
		if(form.vcode.value==null||form.vcode.value.length==0){
			alert("验证码不能为空！");
			return false;
		}
		return true;
	}
function setTitle(){
        var pic = document.getElementById('pic');
        var text_1 = '中盾B/S应用代理系统';
        var text_2 = '';//字母符号数字
        var text = text_1 + text_2; // 注意组合的前后顺序
        var len = text_1.length * 40 + text_2.length * 20;//字母符号为中文的一半
        var pLeft = (pic.clientWidth - len)/2;
        if(pLeft < 300){
            pLeft = 300;
        }
        var p = document.getElementById('text');
        p.style.cssText='float:left;position: relative; top: 50px; left: '+pLeft+'px;';
        p.innerHTML = '<p id="ptext" style="color: #27b1f1;font-size:40px;text-align: center">'+text+'</p>';
    }
</script>
</head>
<body style="background-color: #2e394b;text-align: center" onload="setTitle()">

<div id="pic" style="position: relative;width: 800px; height: 450px; border: 1px solid #01162b;
            margin-left:auto;margin-right:auto;margin-top: 5%;
            background: url(img/login.jpg) no-repeat;"
>
	<div id="text" style="float:left;position: relative;">
	</div>

	<div  style="margin-left:auto;margin-right:auto;margin-top:220px;border: 0px solid #acd67a;
                /*width: 400px;*/
                /*padding: 100px 0 0 350px;*/">
		<form name="loginForm" action="login.action" method="post" onsubmit="return checkForm();">
			<table border=0px style="margin-left:350px;padding: 10px 10px 10px 10px;" id=myTable>
				<tr>
					<td align=right>&nbsp;用户名：</td>
					<td colspan="1"><input type="text" name="name" autocomplete="off" style="width:140px;"/></td>
				</tr>
				<tr>
					<td align=right>&nbsp;密&nbsp;&nbsp;码：</td>
					<td colspan="1"><input type="password" name="pwd" autocomplete="off" style="width:140px;"/></td>
				</tr>
				<tr>
					<td align=right>&nbsp;验证码：</td>
					<td><INPUT TYPE="text" autocomplete="off" NAME="vcode" style="width: 80px;"><img
							src="<c:url value="/RandomCodeCtrl"/>" height="23" width="60"
							align="middle" id="verifyCodeImg" onclick="reloadVerifyCode();"
							alt="单击更换验证码"/></td>
				</tr>
				<tr>
					<td/>
					<td colspan="1"><input type="submit" value="登&nbsp;&nbsp;录"/>&nbsp;&nbsp;<input
							type="reset" value="取&nbsp;&nbsp;消"/></td>
				</tr>
				
			</table>
		</form>
	</div>
	<div id="text_bottom" style="float:left;position: relative; bottom: -50px; left: 500px;">
		<p style="color: #27b1f1;font-size:15px;text-align: center">版权所有 北京中盾安全技术开发有限公司</p>
	</div>
</div>
</body>
</html>

