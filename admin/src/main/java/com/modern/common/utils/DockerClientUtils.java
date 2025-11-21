package com.modern.common.utils;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DockerClientBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/8/15 17:53
 */
@Slf4j
public class DockerClientUtils {

    /**
     * 连接docker服务器
     *
     * @return
     */
    public static DockerClient connectDocker(String serverUrl) {
        DockerClient dockerClient = DockerClientBuilder.getInstance(serverUrl).build();
        Info info = dockerClient.infoCmd().exec();
        log.info("================= Docker环境信息：================= \n{}", info);
        return dockerClient;
    }

    /**
     * 创建容器
     *
     * @param client
     * @param containerName
     * @param imageName
     * @param exposedTcpPort
     * @param bindTcpPort
     * @return
     */
    public static CreateContainerResponse createContainer(DockerClient client, String containerName, String imageName,
                                                   int exposedTcpPort, int bindTcpPort) {
        ExposedPort exposedPort = ExposedPort.tcp(exposedTcpPort);
        Ports portBindings = new Ports();
        portBindings.bind(exposedPort, Ports.Binding.bindPort(bindTcpPort));

        CreateContainerResponse container = client.createContainerCmd(imageName)
                .withName(containerName)
                .withHostConfig(HostConfig.newHostConfig().withPortBindings(portBindings))
                .withExposedPorts(exposedPort).exec();
        return container;
    }

    /**
     * 创建容器
     *
     * @param client
     * @param containerName
     * @param imageName
     * @return
     */
    public static CreateContainerResponse createContainer(DockerClient client, String containerName, String imageName) {
        CreateContainerResponse container = client.createContainerCmd(imageName)
                .withName(containerName).exec();
        return container;
    }


    /**
     * 启动容器
     *
     * @param client
     * @param containerId
     */
    public static void startContainer(DockerClient client, String containerId) {
        client.startContainerCmd(containerId).exec();
    }

    /**
     * 停止容器
     *
     * @param client
     * @param containerId
     */
    public static void stopContainer(DockerClient client, String containerId) {
        client.stopContainerCmd(containerId).exec();
    }


    /**
     * 重启容器
     *
     * @param client
     * @param containerId
     */
    public static void restartContainer(DockerClient client, String containerId) {
        client.restartContainerCmd(containerId).exec();
    }


    /**
     * 删除容器
     *
     * @param client
     * @param containerId
     */
    public static void removeContainer(DockerClient client, String containerId) {
        client.removeContainerCmd(containerId).exec();
    }
}
