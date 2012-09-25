package jenkins.model;

import hudson.DescriptorExtensionList;
import hudson.model.AbstractProject;
import hudson.model.Descriptor;

import java.util.ArrayList;
import java.util.List;

public abstract class CulpritStrategyDescriptor extends Descriptor<CulpritStrategy> {
    protected CulpritStrategyDescriptor(Class<? extends CulpritStrategy> clazz) {
        super(clazz);
    }

    protected CulpritStrategyDescriptor() {
    }

    /**
     * Allows {@link CulpritStrategyDescriptor} to target specific kinds of projects.
     */
    public abstract boolean isApplicable(AbstractProject project);

    public static DescriptorExtensionList<CulpritStrategy,CulpritStrategyDescriptor> all() {
        return Jenkins.getInstance().<CulpritStrategy,CulpritStrategyDescriptor>getDescriptorList(CulpritStrategy.class);
    }

    public static List<CulpritStrategyDescriptor> _for(AbstractProject p) {
        List<CulpritStrategyDescriptor> r = new ArrayList<CulpritStrategyDescriptor>();
        for (CulpritStrategyDescriptor d : all()) {
            if (d.isApplicable(p))
                r.add(d);
        }
        return r;
    }
}
