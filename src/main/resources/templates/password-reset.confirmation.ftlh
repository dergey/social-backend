<#-- @ftlvariable name="frontendContext" type="com.sergey.zhuravlev.social.mail.MailFrontendContext" -->
<#-- @ftlvariable name="root" type="com.sergey.zhuravlev.social.entity.Confirmation" -->

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Confirm password reset</title>
</head>
<body>
<div>Reset password in Social Application</div>
<div>Your code is <b>${root.manualCode}</b></div>
<div>You can also reset your password by following <a href="${frontendContext.getFrontendPasswordResetConfirmationLink(root.manualCode)}"><b>this link</b></a></div>
</body>
</html>