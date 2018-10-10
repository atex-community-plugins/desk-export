package com.atex.plugins.deskexport;

import com.atex.onecms.content.Subject;
import com.atex.onecms.content.SubjectUtil;
import com.polopoly.cm.app.ContentSession;
import com.polopoly.cm.app.Editor;
import com.polopoly.cm.app.Viewer;
import com.polopoly.cm.app.widget.OComplexPolicyWidget;
import com.polopoly.cm.client.CMException;
import com.polopoly.common.logging.LogUtil;
import com.polopoly.orchid.OrchidException;
import com.polopoly.orchid.context.Device;
import com.polopoly.orchid.context.OrchidContext;
import com.polopoly.orchid.util.WidgetUtil;
import com.polopoly.orchid.widget.OJavaScript;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.IOException;

/**
 * Widget that adds a button to export polopoly content to desk
 *
 */
public class ODeskExportWidget extends OComplexPolicyWidget implements Editor, Viewer {

    private static final Logger LOG = LogUtil.getLog(ODeskExportWidget.class.getName());
    private Subject subject =null;
    private String token =null;

    @Override
    public void init(OrchidContext oc) throws OrchidException{
        super.init(oc);

        try {
            subject = SubjectUtil.fromCaller(this.getPolicy().getCMServer().getCurrentCaller());
        }catch(CMException e){
            LOG.log(Level.WARNING, "Encountered a problem while getting the subject from caller: ",e);
        }

        token=  getCurrentToken(this.subject);

        OJavaScript script = new OJavaScript();
        addAndInitChild(oc, script);
        script.setScript("function exportContent(contentId) {\n" +
                "    var xhr = new XMLHttpRequest();\n" +
                "    xhr.open('GET', '/dam/content/export?contentId='  + contentId , true);\n" +
                "    xhr.setRequestHeader('Content-type', 'text/plain');\n" +
                "    xhr.setRequestHeader('X-Auth-Token', '"+ token + "');\n" +
                "    xhr.send();\n" +
                "}");

        WidgetUtil.addHeadJavaScript(oc, script, true);
    }

    public void localRender(OrchidContext oc) throws OrchidException, IOException {
        Device device = oc.getDevice();

        String contentId = getPolicy().getContentId().getContentId().getContentIdString();
        boolean show = getContentSession().getMode() == ContentSession.VIEW_MODE;

        if (show) {
            device.println("<div class='edit-in-act'>");
            device.println("<button type='button' class='' onclick='exportContent("+contentId+")' title='Export to Desk'> Export to Desk </button>");
            device.println("</div>");
        }
    }

    private String getCurrentToken(Subject subject) {
        String principalIdString = subject.getPrincipalId();
        return principalIdString + "::" + subject.getPublicCredentials();
    }
}
