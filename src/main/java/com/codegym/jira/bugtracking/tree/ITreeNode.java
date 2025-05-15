package com.codegym.jira.bugtracking.tree;

import com.codegym.jira.common.HasIdAndParentId;

import java.util.List;

public interface ITreeNode<T extends HasIdAndParentId, R extends ITreeNode<T, R>> {
    List<R> subNodes();
}
