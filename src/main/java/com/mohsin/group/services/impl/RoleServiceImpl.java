package com.mohsin.group.services.impl;

import com.mohsin.group.repositories.AuthorityRepository;
import com.mohsin.group.repositories.GroupRepository;
import com.mohsin.group.repositories.RoleRepository;
import com.mohsin.group.dtos.group.AuthorityRole;
import com.mohsin.group.dtos.role.*;
import com.mohsin.group.entities.Authority;
import com.mohsin.group.entities.Group;
import com.mohsin.group.entities.Role;
import com.mohsin.group.exceptions.*;
import com.mohsin.group.mapper.RoleMapper;
import com.mohsin.group.services.RoleService;
import com.mohsin.group.utils.GlobalRole;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final GroupRepository groupRepository;

    private final  RoleRepository roleRepository;

    private final AuthorityRepository authorityRepository;

    private RoleMapper roleMapper;

    @Override // MANAGE_ROLE
    public Page<RoleResponse> getRoles(Long groupId  , Pageable pageable) {
        Group group = groupRepository.findOrThrow(groupId);

        Page<Role>  roles = roleRepository.getRolesOfGroup(group ,pageable);

        return roles.map(roleMapper::toRoleResponse);
    }

    @Override // MANAGE_ROLE
    public RoleResponse getRole(GetRoleRequest request) {
        Group group = groupRepository.findOrThrow(request.getGroupId());

        String roleName = request.getRoleName();

        if (!GlobalRole.GLOBAL_ROLE.contains(roleName)){ // CUSTOM ROLE ARE SPECIFIC FOR A KNOWN GROUP

            Role role =roleRepository.findOrThrow(roleName , group);

            return roleMapper.toRoleResponse(role);
        }

        Role role = roleRepository.findOrThrow(roleName);

        return roleMapper.toRoleResponse(role);
    }

    @Override // MANAGE_ROLE
    public RoleResponse createRole(CreateRoleRequest request) {
        Group group = groupRepository.findOrThrow(request.getGroupId());

        if(GlobalRole.GLOBAL_ROLE.contains(request.getName()))
            throw new GlobalRoleNameForbiddenException(request.getName());

        String roleName = request.getName();

        Role role =roleRepository
                .findByNameAndGroup(roleName ,group)
                .orElse(null);

        if(role != null) throw new RoleAlreadyExistException(roleName);

        Role r = Role.builder()
                    .name(request.getName())
                    .group(group)
                    .authorities(new HashSet<>())
                    .build();

        Role savedRole = roleRepository.save(r);

        Authority member = authorityRepository.findOrThrow(GlobalRole.MEMBER);

        savedRole.getAuthorities().add(member);

        Role roleWithAuthorityMember = roleRepository.save(savedRole);

        return roleMapper.toRoleResponse(roleWithAuthorityMember);
    }

    @Override // MANAGE_ROLE
    public RoleResponse updateRole(UpdateRoleRequest request) {
        Group group = groupRepository.findOrThrow(request.getGroupId());

        String roleName = request.getRoleName();

        Role role =roleRepository.findOrThrow(roleName , group);

        if(request.getName() != null) role.setName(request.getName());

        Role savedRole = roleRepository.save(role);

        return roleMapper.toRoleResponse(savedRole);
    }

    @Override // MANAGE_ROLE
    public void deleteRole(DeleteRoleRequest request) {
        Group group = groupRepository.findOrThrow(request.getGroupId());

        String roleName = request.getRoleName();

        Role role = roleRepository.findOrThrow(roleName , group);

        roleRepository.delete(role);
    }

    @Override // MANAGE_ROLE
    public RoleResponse attachAuthority(AuthorityRole request) {
        Group group = groupRepository.findOrThrow(request.getGroupId());

        String roleName = request.getRoleName();

        if(GlobalRole.GLOBAL_ROLE.contains(roleName))
            throw new GlobalRoleNameForbiddenException(roleName);

        Role role =roleRepository.findOrThrow(roleName , group);

        Authority authority = authorityRepository
                .findOrThrow(request.getAuthorityName());

        if(role.getAuthorities().contains(authority))
            throw new RoleAlreadyHaveAuthorityException(role , authority);

        role.getAuthorities().add(authority);

        Role savedRole = roleRepository.save(role);

        return roleMapper.toRoleResponse(savedRole);
    }

    @Override // MANAGE_ROLE
    public RoleResponse detachAuthority(AuthorityRole request) {
        Group group = groupRepository.findOrThrow(request.getGroupId());

        String roleName = request.getRoleName();

        if(GlobalRole.GLOBAL_ROLE.contains(roleName))
            throw new GlobalRoleNameForbiddenException(roleName);

        Role role =roleRepository.findOrThrow(roleName , group);

        Authority authority = authorityRepository.findOrThrow(request.getAuthorityName());

        if(!role.getAuthorities().contains(authority)){
            throw new RoleNotContainAuthorityException(role , authority);
        }

        role.getAuthorities().remove(authority);

        Role savedRole = roleRepository.save(role);

        return roleMapper.toRoleResponse(savedRole);

    }

}
