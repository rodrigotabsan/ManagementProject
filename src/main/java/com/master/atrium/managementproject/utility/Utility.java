package com.master.atrium.managementproject.utility;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.master.atrium.managementproject.entity.Project;

public class Utility {

	private Utility() {
		super();
	}
	
	public static List<Project> convertIterableProjectToListProject(Iterable<Project> projectsselected) {
		return StreamSupport.stream(projectsselected.spliterator(), false)
			    .collect(Collectors.toList());
	}
	
}
