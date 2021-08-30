package ru.progwards.java2.lessons.less15.vm;

import java.util.ArrayList;
import java.util.List;

public class VirtualMachine {
    private String name;
    private String region;
    private String resourceGroup;
    private String network; // необязательно
    private String subnet; // необязательно
    private String processor;
    private List<String> disks;
    private String privateIP; // необязательно
    private String publicIP;
    private String os;

    private VirtualMachine() {
    }

    @Override
    public String toString() {
        return "VirtualMachine{" +
                "name='" + name + '\'' +
                ", region='" + region + '\'' +
                ", resourceGroup='" + resourceGroup + '\'' +
                ", network='" + network + '\'' +
                ", subnet='" + subnet + '\'' +
                ", processor='" + processor + '\'' +
                ", disks=" + disks +
                ", privateIP='" + privateIP + '\'' +
                ", publicIP='" + publicIP + '\'' +
                ", os='" + os + '\'' +
                '}';
    }

    // вложенный класс строителя
    public static class Builder {
        private VirtualMachine vm;

        public Builder(String name, String region, String resourceGroup) {
            vm = new VirtualMachine();
            vm.name = name;
            vm.region = region;
            vm.resourceGroup = resourceGroup;
            vm.disks = new ArrayList<>();
        }

        public Builder withNetwork(String network) {
            vm.network = network;
            return this;
        }

        public Builder withSubnet(String subnet) {
            vm.subnet = subnet;
            return this;
        }

        public Builder withProcessor(String processor) {
            vm.processor = processor;
            return this;
        }

        public Builder withDisk(String disk) {
            vm.disks.add(disk);
            return this;
        }

        public Builder withPrivateIP(String privateIP) {
            vm.privateIP = privateIP;
            return this;
        }

        public Builder withPublicIP(String publicIP) {
            vm.publicIP = publicIP;
            return this;
        }

        public Builder withOs(String os) {
            vm.os = os;
            return this;
        }

        public VirtualMachine build() {
            // проверка полноты и целостности данных
            if (vm.processor == null || vm.disks.isEmpty() || vm.os == null)
                throw new IllegalStateException();
            // возврат сформированного объекта виртуальной машины
            return vm;
        }
    }
        public static void main(String[] args) {
            VirtualMachine vm =
                    new VirtualMachine.Builder("Комп 1", "US WEST", "Progwards")
                            .withProcessor("2 ядра 3 ГГц")
                            .withDisk("30 Гб SSD")
                            .withDisk("128 Гб HDD")
                            .withDisk("1 Тб HDD")
                            .withPublicIP("Dinamic IP")
                            .withOs("Windows Server 2016")
                            .build();
            System.out.println("\n***********************");
            System.out.println(vm);
            System.out.println("***********************");
        }
    }
