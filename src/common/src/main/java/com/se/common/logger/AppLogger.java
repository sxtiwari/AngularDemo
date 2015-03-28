package com.se.common.logger;

import com.se.common.util.RequestContext;

public interface AppLogger {

    public void error (String message, Object... parms);
    
    public void error (RequestContext context, String message, Object... parms);

    public void info (String message, Object... parms);

    public void info (RequestContext context, String message, Object... parms);

    public void warn (String message, Object... parms);

    public void warn (RequestContext context, String message, Object... parms);

    public void debug (String message, Object... parms);

    public void debug (RequestContext context, String message, Object... parms);

    public void trace (String message, Object... parms);

    public void trace (RequestContext context, String message, Object... parms);
}
