package me.ztkmk.auth.enumeration

/**
 * @author Kebron ztkmkoo@gmail.com
 * @create 2021-03-21 23:51
 */
enum class UserAuthenticationStatus {
    // cellphone or (device id and cellphone) were not registered
    NEW_USER {
        override val action: AuthenticationAction = AuthenticationAction.AUTH_SMS
        override val message: String = "New user! Try to authenticate user phone number."
    },
    // device id and cellphone were registered
    REGISTERED_DEVICE {
        override val action: AuthenticationAction = AuthenticationAction.LOGIN
        override val message: String = "Registered device! Try to login."
    },
    // cellphone was registered
    REGISTERED_USER{
        override val action: AuthenticationAction = AuthenticationAction.AUTH_SMS
        override val message: String = "Unregistered device! Try to authenticate user phone number."
    },
    ;

    abstract val action: AuthenticationAction
    abstract val message: String
}