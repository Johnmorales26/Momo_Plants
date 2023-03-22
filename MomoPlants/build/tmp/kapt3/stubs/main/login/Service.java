package login;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000bJ\u0006\u0010\u0014\u001a\u00020\u0012J\u0016\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0010R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0017"}, d2 = {"Llogin/Service;", "", "()V", "authenticatedUser", "", "getAuthenticatedUser", "()Z", "setAuthenticatedUser", "(Z)V", "registeredUsers", "", "Llogin/User;", "getRegisteredUsers", "()Ljava/util/List;", "existeUsuario", "username", "", "login", "", "user", "logout", "registrarUsuario", "password", "MomoPlants"})
public final class Service {
    private boolean authenticatedUser = false;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<login.User> registeredUsers = null;
    
    public Service() {
        super();
    }
    
    public final boolean getAuthenticatedUser() {
        return false;
    }
    
    public final void setAuthenticatedUser(boolean p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<login.User> getRegisteredUsers() {
        return null;
    }
    
    public final void login(@org.jetbrains.annotations.NotNull
    login.User user) {
    }
    
    public final void logout() {
    }
    
    public final void registrarUsuario(@org.jetbrains.annotations.NotNull
    java.lang.String username, @org.jetbrains.annotations.NotNull
    java.lang.String password) {
    }
    
    public final boolean existeUsuario(@org.jetbrains.annotations.NotNull
    java.lang.String username) {
        return false;
    }
}