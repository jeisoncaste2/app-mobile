import React, { useEffect, useState } from 'react';
import { View, Text, FlatList, TouchableOpacity, Alert, TextInput, Modal, ActivityIndicator, ScrollView} from 'react-native';
import { categoriesStyles } from '../styles/CategoriesStyles';
import { CategoryService, authService } from '../services/api';

export default function CategoriesScreen() {
    const [categories, setCategories] = useState<any[]>([]);
    const [loading, setLoading] = useState(false);
    const [modalVisible, setModalVisible] = useState(false);
    const [editing, setEditing] = useState<any>(null);
    const [formData, setFormData] = useState({ name: '', description: '' });
    const [error, setError] = useState('');
    const [currentUser, setCurrentUser] = useState<any>(null);
    useEffect(() => {
        loadCurrentUser();

}, []);
const loadCurrentUser = async () => {
    try {
        const user = await authService.getCurrentUser();
        setCurrentUser(user);

    }catch (error) {
        console.error('Error al cargar usuario:', error);
    }
}
const loadCategories = async () => {
        setLoading(true);
        setError('');
        try {
            const response = await CategoryService.getAll();
            setCategories(response.data || []);
        } catch (error) {
            setError('Error al cargar categorías');
            setCategories([]);
        } finally {
            setLoading(false);
        }
    };
    const handleSave = async () => {
        if (!formData.name.trim()) {
            Alert.alert('Error', 'El nombre es obligatorio');
            return;
        }
        try {
            if (editing) {
                await CategoryService.update(editing.id, formData);
                Alert.alert('Éxito', 'Categoría actualizada exitosamente');
            } else {
                await CategoryService.create(formData);
                Alert.alert('Éxito', 'Categoría creada exitosamente');
            }
            setModalVisible(false);
            resetForm();
            loadCategories();
        } catch (error) {
            Alert.alert('Error', 'Hubo un problema al guardar la categoría');
}
    };
    const handleDelete = (item: any) => {
        if (currentUser?.role !== 'ADMIN') {
            Alert.alert('Error', 'No tienes permisos para eliminar categorías');
            return;
        }
            Alert.alert('Confirmar', `¿Eliminar  ${item.name}?`, [
                { text: 'Cancelar', style: 'cancel' },
                {
                    text: 'Eliminar',
                    style: 'destructive',
                    onPress: async () => {
                        try {
                            await CategoryService.delete(item.id);
                            Alert.alert('Éxito', 'Categoría eliminada exitosamente');
                            loadCategories();
                        } catch (error) {
                            Alert.alert('Error', 'Hubo un problema al eliminar la categoría');
                        }

                    }
                }
            ]);                    
        }
        const handleToggleActive =  (item: any) => {
            const action = item.active ? 'Desactivar' : 'Activar';
            Alert.alert('Confirmar', `¿${action} ${item.name}?`, [
                { text: 'Cancelar', style: 'cancel' },
                {
                    text: action.charAt(0).toUpperCase() + action.slice(1),
                    onPress: async () => {
                        try {
                            await CategoryService.update(item.id, { 
                                name: item.name,
                                description: item.
                                description,
                                active: !item.active

                        });
                            Alert.alert('Éxito', `Categoría ${item.active ? 'desactivada' : 'activada'}`);
                            loadCategories();
                        } catch (error) {
                            Alert.alert('Error', `Hubo un problema al ${action}`);
                        }
                    }
                }
            ]);    
        }
        const handleEdit = (item: any) => {
            setFormData({ name: item.name, description: item.description || '' });
            setEditing(item);
            setModalVisible(true);
        };
        const resetForm = () => {
            setFormData({ name: '', description: '' });
            setEditing(null);
        };
        const renderCategory = {{item: any}} => (
            <View style={categoriesStyles.categoryCard}>
            <View style={categoriesStyles.categoryInfo}>
            <Text style={categoriesStyles.categoryName}>
                {item.name} { !item.active && <Text style={{color:'#999'}}>(Inactivo)</Text> }
            </Text>
            {item.description && <Text style={categoriesStyles.categoryDescription}>{item.description}</Text>}
            </View>
            <View style={categoriesStyles.actionsContainer}>
                <TouchableOpacity 
                    style={[categoriesStyles.actionButton,categoriesStyles.editButton]}
                    onPress={() => handleEdit(item)}
                >
                <Text style={[categoriesStyles.actionButtonText,categoriesStyles.editButtonText]}>Editar</Text>
                </TouchableOpacity>
                <TouchableOpacity 
                    style={[categoriesStyles.actionButton,item .active ? categoriesStyles.deleteButton : categoriesStyles.editButton]}
                    onPress={() => handleToggleActive(item)}
                >
                <Text style={[categoriesStyles.actionButtonText,item.active ? categoriesStyles.deleteButtonText : categoriesStyles.editButtonText]}>
                    {item.active ? 'Desactivar' : 'Activar'}</Text>

                </TouchableOpacity>
                {currentUser?.role === 'ADMIN' && (
                    <TouchableOpacity
                        style={[categoriesStyles.actionButton,categoriesStyles.deleteButton]}
                        onPress={() => handleDelete(item)}
                    >
                        <Text style={[categoriesStyles.actionButtonText,categoriesStyles.deleteButtonText]}>Eliminar</Text>
                    </TouchableOpacity>
                )}
            </View>
        </View>

        );
        if (loading) {
        return (
            <View style={categoriesStyles.container}>
                <ActivityIndicator size="large" color="#007AFF" />
                <Text style={categoriesStyles.loadingText}>Cargando...</Text>
            </View>
        );

}
    return (
        <View style={categoriesStyles.container}>
        <View style={categoriesStyles.header}>
            <View style={categoriesStyles.headerContent}>
                <Text style={categoriesStyles.headerTitle}>Gestionar Categorías</Text>
                <TouchableOpacity
                    style={categoriesStyles.addButton}
                    onPress={() => {
                        resetForm();
                        setModalVisible(true);
                    }}
                    >
                        <Text style={categoriesStyles.addButtonText}>+ Nueva </Text>
                </TouchableOpacity>
            </View>
        </View>
        {error ? (
            <View style={categoriesStyles.errorContainer}>
                <Text style={categoriesStyles.errorText}>{error}</Text>
                <TouchableOpacity style={categoriesStyles.retryButtonText}
                   onPress={loadCategories}
                >
                    <Text style={categoriesStyles.retryButtonText}>Reintentar</Text>
                </TouchableOpacity>
            </View>
            ) : null}
            <FlatList
                data={categories}
                renderItem={renderCategory}
                keyExtractor={(item) => item.id?.toString() || ''}
                contentContainerStyle={categoriesStyles.listContainer}
                showsVerticalScrollIndicator={false}
                ListEmptyComponent={
                    !loading && !error ? (
                        <View style={categoriesStyles.emptyContainer}>
                            <Text style={categoriesStyles.emptyText}>
                                No hay categorías
                            </Text>
                            <Text style={categoriesStyles.emptySubText}> Agrega nuevas categorías para empezar</Text>

                        </View>
                    ) : null
                }
            />
            <Modal animationType="slide" transparent={true} 
                visible={modalVisible}>
                    <View style={categoriesStyles.modalOverlay}>
                        <View style={categoriesStyles.modalContent}>
                            <ScrollView>
                                <View Style={categoriesStyles.modalHeader}>
                                    <Text style={categoriesStyles.modalTitle}>
                                        {editing ? 'Editar Categoría' : 'Nueva Categoría'}
                                    </Text>
                                </View>
                                <View style={categoriesStyles.formContainer}>
                                    <View style={categoriesStyles.inputGroup}>
                                        <Text style={categoriesStyles.inputLabel}>Nombre *</Text>
                                        <TextInput
                                            style={categoriesStyles.input}
                                            value={formData.name}
                                            onChangeText={(text) => setFormData({ ...formData, name: text })}
                                            placeholder="Nombre de la categoría"
                                            placeholderTextColor="#999"
                                            />
                                            </View>
                                        </View>
                                    </ScrollView>
                                </View>

                                <View style={categoriesStyles.inputGroup}>
                                        <Text style={categoriesStyles.inputLabel}>Descripcion *</Text>
                                        <TextInput
                                            style={[categoriesStyles.input, categoriesStyles.textArea]}
                                            value={formData.name}
                                            onChangeText={(text) => setFormData({ ...formData, name: text })}
                                            placeholder="Descripcion opcional"
                                            placeholderTextColor="#999"
                                            numberOfLines={3}
                                            textAlignVertical="top"
                                            />
                                            </View>
                                        </View>
                                        <View style={categoriesStyles.modalButtons}>
                                            <TouchableOpacity
                                                style={[categoriesStyles.modalButton,categoriesStyles.cancelButton]}
                                                onPress={() => setModalVisible(false)}>

                                                </TouchableOpacity>
                                                <TouchableOpacity
                                                style={[categoriesStyles.modalButton,categoriesStyles.saveButton]}
                                                onPress={handleSave}>
                                                <Text style={[categoriesStyles.modalButtonText,categoriesStyles.saveButtonText]}>
                                                    {editing ? 'Actualizar' : 'Guardar'}
                                                </Text>
                                            </TouchableOpacity>
                                        </View>
                                    <ScrollView>
                                </View>
                    </View>
            </Modal>
    </View>
    );
}
                                            